import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import PortalClient from "../api/portalClient";

class PortalPage extends BaseClass {

        constructor() {
            super();
            this.bindClassMethods(['onGet', 'renderPortfolio'], this);
            this.dataStore = new DataStore();
        }

        async mount() {
            this.client = new PortalClient();
            this.onGet();

            this.dataStore.addChangeListener(this.renderPortfolio)
        }

        async renderPortfolio() {
            let resultArea = document.getElementById("results-area");

            const portfolio = this.dataStore.get("portfolio");

            let Session = window.sessionStorage;
            if (portfolio) {
                console.log(portfolio);
                let portfolioStocks = portfolio.items;
//                let count = 0;
//                const cells = ["cellA1", "cellA2", "cellA3", "cellA4", "cellA5", "cellB1", "cellB2", "cellB3", "cellB4", "cellB5", "cellC1", "cellC2", "cellC3", "cellC4", "cellC5"];
//                for(let i = 0; i < 15; i+=5){
//                    let div = document.getElementById(cells[i]);
//                    let div2 = document.getElementById(cells[i+1]);
//                    let div3 = document.getElementById(cells[i+2]);
//                    let div4 = document.getElementById(cells[i+3]);
//                    let div5 = document.getElementById(cells[i+4]);
//                    div.innerHTML = portfolioStocks[count].symbol.s;
//                    div2.innerHTML = portfolioStocks[count].quantity.n;
//                    div3.innerHTML = portfolioStocks[count].purchasePrice.n;
//                    div5.innerHTML = portfolioStocks[count].purchaseDate.s;
//                    div4.innerHTML = portfolioStocks[count].quantity.n * portfolioStocks[count].purchasePrice.n;
//                    count++;
//                }
                let finale = "<table border='1' width='90%'><tr><th style='background-color: #B894FF; height: 3px;'>Symbol</th><th style='background-color: #B894FF; height: 3px;'>Quantity</th><th style='background-color: #B894FF; height: 3px;'>Purchase Price</th><th style='background-color: #B894FF; height: 3px;'>Price Paid</th><th style='background-color: #B894FF; height: 3px;'>Purchase Date</th></tr>";
                let divy = document.getElementById("stocklist");
                for(let i = 0; i < portfolioStocks.length; i++){
                    finale += "<tr><td>" + portfolioStocks[i].symbol.s + "</td><td>" + portfolioStocks[i].quantity.n + "</td><td>" + portfolioStocks[i].purchasePrice.n + "</td><td>" + portfolioStocks[i].quantity.n * portfolioStocks[i].purchasePrice.n + "</td><td>" + portfolioStocks[i].purchaseDate.s + "</td></tr>";
                }
                finale += "</table>";
                divy.innerHTML = finale;
            } else {
                resultArea.innerHTML = "";
            }
        }

        async onGet() {

            let id = "userId";
            this.dataStore.set("portfolio", null);

            let result = await this.client.getPortfolio(id, this.errorHandler);
            console.log(result.data);
            this.dataStore.set("portfolio", result);

            if (result) {
                this.showMessage("Got Portfolio!");
            } else {
                this.errorHandler("Error doing GET!  Try again...");
            }
        }
}

const main = async () => {
    const portalPage = new PortalPage();
    portalPage.mount();
    console.log("Mounted!");
};
window.addEventListener('DOMContentLoaded', main);