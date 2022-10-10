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
            document.getElementById('re-fresh').addEventListener('submit', this.onGet);
            this.client = new PortalClient();

            this.dataStore.addChangeListener(this.renderPortfolio)
        }

        async renderPortfolio() {
            let resultArea = document.getElementById("results-area");

            const portfolio = this.dataStore.get("portfolio");

            let Session = window.sessionStorage;
            if (portfolio) {
                let portfolioString = portfolio.items;
                console.log(portfolioString);
                for(let i = 0; i < portfolioString.length; i++) {
                    let stocky = portfolioString[i];
                    let stockString = stocky.symbol.s + " " + stocky.purchaseDate.s + " " + stocky.quantity.n + " " + stocky.purchasePrice.n;
                    console.log(stockString);
                    let stockDiv = document.createElement("div");
                    stockDiv.innerHTML = stockString;
                    resultArea.appendChild(stockDiv);
                }
                resultArea.innerHTML = `
                    <div>ID: ${Session.getItem("userId")}</div>
                    <div>Name: ${Session.getItem("stockSymbol")}</div>
                    <div>Quantity: ${Session.getItem("shares")}</div>
                    <div>Result: ${portfolio}</div>
                `
            } else {
                resultArea.innerHTML = "No Item";
            }
        }

        async onGet(event) {
            // Prevent the page from refreshing on form submit
            event.preventDefault();

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
    const examplePage = new PortalPage();
    examplePage.mount();
};

window.addEventListener('DOMContentLoaded', main);