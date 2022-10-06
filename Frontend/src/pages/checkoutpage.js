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
    for(let pair of queryString.entries()) {
        stock[index] = pair[1];
        // console.log("Key is:" + pair[0]);
        // console.log("Value is:" + pair[1]);
        index +=1;
    }
    let purchasedate = new Date(stock[4].toString());
    console.log("symbol: "+stock[0]);
    console.log("currentprice: "+stock[1]);
    console.log("purchaseprice: "+stock[2]);
    console.log("purchasedate: "+purchasedate.toLocaleDateString());

    let resultArea = document.getElementById("purchase");
    let result = "";
        result += `<h4>${stock[0]}</h4><br>`
        result += `Symbol: ${stock[1]}<br>`
        result += `Current Price: ${stock[2]}<br>`
        result += `Purchase Price: ${stock[3]}<br>`
        result += `Purchase Date: ${purchasedate.toLocaleDateString()}<br>`
    resultArea.innerHTML = result;
}

