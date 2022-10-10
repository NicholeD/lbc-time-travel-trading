import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CheckoutClient from "../api/checkoutClient";

window.onload = function() {
    getParameters();
};
function getParameters() {
    let urlString =
        window.location.href;
    let paramString = urlString.split('?')[1];
    let queryString = new URLSearchParams(paramString);
    const stock = ["name","symbol","currentprice","purchaseprice","purchasedate"];
    let index = 0;
    let funds = 100000.00
    let dollars = Intl.NumberFormat('en-US');
    let quantity = 1;

    for(let pair of queryString.entries()) {
        stock[index] = pair[1];
        // console.log("Key is:" + pair[0]);
        // console.log("Value is:" + pair[1]);
        index +=1;
    }
    let purchasedate = new Date(stock[4].toString());
    let net = stock[2]-stock[3];
//call result Function
    setResult(stock, net, quantity,dollars,purchasedate, funds)

    var updatebutton = document.getElementById('update')
    updatebutton.addEventListener('click', function (event){
        quantity = document.getElementById('quantity').value
        console.log('$'+quantity*net)
        setResult(stock, net, quantity,dollars,purchasedate, funds)
    })
    main(stock);
}

function setResult(stock, net,quantity,dollars, purchasedate, funds){
    let resultArea = document.getElementById("purchase");
    let result = "";
    result += `<h4>${stock[0]}</h4><br>`
    result += `Symbol: ${stock[1]}<br>`
    result += `Current Price: ${stock[2]}<br>`
    result += `Purchase Price: ${stock[3]}<br>`
    result += `Purchase Date: ${purchasedate.toLocaleDateString()}<br><br>`
    if(net > 0)
        result += `Realized Profit: $${dollars.format(net*quantity)}<br>`
    else
        result += `Realized Loss: $${dollars.format(net*quantity)}<br>`
    result +=`</br><div>Avail funds for trading: $${dollars.format(funds)}</div>`
    resultArea.innerHTML = result;
}

class CheckoutPage extends BaseClass {
     constructor() {
         super();
         this.bindClassMethods(['onBuy', 'renderPurchase'], this);
         this.dataStore = new DataStore();
     }
     async mount(stock) {
         document.getElementById('buy').addEventListener('click', (event) => this.onBuy(event, stock));
         this.client = new CheckoutClient();

         //this.dataStore.addChangeListener(this.renderPurchase());
     }

     // Render Methods ----------------------------------------------------
     async renderPurchase() {
         let resultArea = document.getElementById("purchase");

         const stock = ["name","symbol","currentprice","purchaseprice","purchasedate"];
         let purchasedate = new Date(stock[4].toString());
         let net = stock[2]-stock[3];

         let result = "";
         result += `<h4>${stock[0]}</h4><br>`
         result += `Symbol: ${stock[1]}<br>`
         result += `Current Price: ${stock[2]}<br>`
         result += `Purchase Price: ${stock[3]}<br>`
         result += `Purchase Date: ${purchasedate.toLocaleDateString()}<br><br>`
//         if(net > 0)
//             result += `Realized Profit: $${dollars.format(net*quantity)}<br>`
//         else
//             result += `Realized Loss: $${dollars.format(net*quantity)}<br>`

//         result +=`</br><div>Avail funds for trading: $${dollars.format(funds)}</div>`
//        result += `Total Cost: $${dollars.format(100000.00 - (net*quantity))}<br>`
         resultArea.innerHTML = result;
     }

     // Event Handlers ----------------------------------------------------

        async onBuy(event, stock) {
            event.preventDefault();
            let buyButton = document.getElementById('buy');
            buyButton.innerText = 'Buying...';
            buyButton.disabled = true;

            let userId = "userId";
            let stockSymbol = stock[1];
            let purchasePrice = Number(stock[3]);
            let shares = Number(document.getElementById('quantity').value);
            let purchaseDate = stock[4];

            let purchasedStockRequest = [userId, stockSymbol, purchasePrice, shares, purchaseDate];
            this.dataStore.set("stock", null);
            let purchased = await this.client.buyStock(purchasedStockRequest, this.errorHandler);
            this.dataStore.set("stock", purchased);
            let Session = window.sessionStorage;
            Session.setItem("userId", userId);
            if(Session.getItem("stockSymbol") != null && Session.getItem("stockSymbol") != stockSymbol){
                Session.setItem("stockSymbol", Session.getItem("stockSymbol") + ", " + stockSymbol);
            } else {
                Session.setItem("stockSymbol", stockSymbol);
            }
            if(Session.getItem("shares") != null && Session.getItem("stockSymbol") != stockSymbol){
                Session.setItem("shares", Session.getItem("shares") + ", " + shares);
            } else {
                Session.setItem("shares", shares);
            }
            console.log(Session);

            if(purchased) {
                this.showMessage(`Purchased ${purchased.name} stock!`)
            } else {
                this.errorHandler("Error purchasing stock! Try again.")
            }
        }

}
const main = async (stock) => {
    const checkoutPage = new CheckoutPage();
    await checkoutPage.mount(stock);
};