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
                console.log(portfolio);
                let portfolioStocks = portfolio.items;
                let count = 0;
                const cells = ["cellA1", "cellA2", "cellA3", "cellA4", "cellA5", "cellB1", "cellB2", "cellB3", "cellB4", "cellB5", "cellC1", "cellC2", "cellC3", "cellC4", "cellC5"];
                for(let i = 0; i < 15; i+=5){
                    let div = document.getElementById(cells[i]);
                    let div2 = document.getElementById(cells[i+1]);
                    let div3 = document.getElementById(cells[i+2]);
                    let div4 = document.getElementById(cells[i+3]);
                    let div5 = document.getElementById(cells[i+4]);
                    div.innerHTML = portfolioStocks[count].symbol.s;
                    div2.innerHTML = portfolioStocks[count].quantity.n;
                    div3.innerHTML = portfolioStocks[count].purchasePrice.n;
                    div5.innerHTML = portfolioStocks[count].purchaseDate.s;
                    div4.innerHTML = portfolioStocks[count].quantity.n * portfolioStocks[count].purchasePrice.n;
                    count++;
                }
            } else {
                resultArea.innerHTML = "";
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
    const portalPage = new PortalPage();
    portalPage.mount();
    console.log("Mounted!");
};
window.addEventListener('DOMContentLoaded', main);