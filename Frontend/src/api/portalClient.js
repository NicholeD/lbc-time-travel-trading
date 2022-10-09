// * Copyright (c) 2022 All Rights Reserved
// * Title: Time Travel Trading
// * Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett

import BaseClass from "../util/baseClass"
import axios from 'axios'

export default class PortalClient extends BaseClass {
    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'getPortfolio'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady();
        }
    }

    async getPortfolio(userId, errorCallBack) {
        try {
            const response = await this.client.get(`/purchasedstocks/portfolio/${userId}`);
            return response.data;
        } catch (error) {
            this.handleError("getPortfolio", error, errorCallBack);
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
