// * Copyright (c) 2022 All Rights Reserved
// * Title: Time Travel Trading
// * Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett

import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class CheckoutClient extends BaseClass{
    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'buyStock'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);

    }
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }
    async buyStock(purchasedStockRequest, errorCallback){
        try {
            console.log(purchasedStockRequest);
            const response = await this.client.post(`/purchasedstocks`, {
                userId: purchasedStockRequest[0],
                stockSymbol: purchasedStockRequest[1],
                shares: purchasedStockRequest[3],
                purchasePrice: purchasedStockRequest[2],
                purchaseDate: purchasedStockRequest[4],
                orderDate: purchasedStockRequest[4]
            })
            console.log(response.data);
            return response.data;
        } catch (error) {
            this.handleError("buyStock", error, errorCallback);
        }
    }

    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }

}