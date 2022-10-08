import DataStore from "../util/DataStore";

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
        async mount() {
            document.getElementById('buy').addEventListener("submit", this.onBuy);
            //TODO - this.client = new CheckoutClient() or PortalClient()

            this.dataStore.addChangeListener(this.renderPurchase());
        }

        // Render Methods ----------------------------------------------------
        async renderPurchase() {


        }

        // Event Handlers ----------------------------------------------------

        async onBuy(event) {


        }

        async onUpdate(event) {


        }

}

