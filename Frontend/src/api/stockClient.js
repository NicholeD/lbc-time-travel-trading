// * Copyright (c) 2022 All Rights Reserved
// * Title: Time Travel Trading
// * Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett


import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class StockClient extends BaseClass{
    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'getStocks'];
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
    async getStocks(symbol, errorCallback){
        try{
            const response = await this.client.get(`/stocks/${symbol}`);
            return response.data;
        }catch (error){
            this.handleError("getConcert", error, errorCallback)
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