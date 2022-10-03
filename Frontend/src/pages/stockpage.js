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
            resultArea.innerHTML = `
                <div>ID: ${stock.symbol}</div>
                <div>Name: ${stock.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Items found";
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