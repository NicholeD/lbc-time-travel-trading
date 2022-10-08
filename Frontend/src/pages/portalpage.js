/**
* Logic for the purchase of a stock
*/
import BaseClass from "../util/baseClass";


class Portalpage extends BaseClass {

    //TODO - come back to this and finish
    constructor() {
        super();
    }
    /**
     * Once the page has loaded, set up the event handlers and fetch purchasedStock object
     */

    async mount() {
        document.getElementById('').addEventListener('submit', this.onBuy);


    }

    // Render Methods ----------------------------------------------------



}


const main = async () => {
    console.log('I created a new page!');
};

window.addEventListener('DOMContentLoaded', main);