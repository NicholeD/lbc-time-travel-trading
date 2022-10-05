// * Copyright (c) 2022 All Rights Reserved
// * Title: Time Travel Trading
// * Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett

import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import StockClient from "../api/stockClient";
import ExampleClient from "../api/exampleClient";


class StockPage extends BaseClass{
    constructor() {
        super();
        this.bindClassMethods(['onGet', 'renderStock'], this);
        this.dataStore = new DataStore();
    }
    async mount() {
        document.getElementById('formid').addEventListener('submit', this.onGet);
        this.client = new StockClient();

        this.dataStore.addChangeListener(this.renderStock)
    }
    async renderStock(){
        let resultArea = document.getElementById("stockresults");

        const stock = this.dataStore.get("stock");
        if (stock) {
            let stockStocks = stock.stocks;
            let result = ""
            result += `<div>Stock Name: ${stock.name}</div>`
            result += `<div>Stock Symbol: ${stock.symbol}</div>`
            result += "<ul>"
            for (let stock of stockStocks) {
                result += `<div class="stock"><h3>\$${stock.purchasePrice}</h3>${stock.purchaseDate}<a class="hyperlink" href="checkout.html?symbol=${stock.symbol}&currentprice=${stockStocks[0].purchasePrice}&purchaseprice=${stock.purchasePrice}&purchasedate=${stock.purchaseDate}"><span></br></span></a></div>`;
            }
            result += "</ul>";
            resultArea.innerHTML = result

        } else {
            resultArea.innerHTML = "Searching...";
        }
    }

    async onGet(event){
        event.preventDefault();

        let symbol = document.getElementById("searchstock").value;
        this.dataStore.set("stock", null);
        let result = await this.client.getStocks(symbol, this.errorHandler);
        this.dataStore.set("stock", result);
        console.log(result);

        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

}

const main = async () => {
    const stockPage = new StockPage();
    stockPage.mount();
};
window.addEventListener('DOMContentLoaded', main);