import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ContactClient from "../api/contactClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ContactPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetContact', 'onCreate', 'renderContacts'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('contactform').addEventListener('submit', this.onCreate);
        this.client = new ContactClient();

        this.dataStore.addChangeListener(this.renderContacts);
        await this.onGetContact();
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderContacts() {
        let resultArea = document.getElementById("result-info");

        const contact = this.dataStore.get("contact");

        if (contact) {
            resultArea.innerHTML = `
                <div class="form">Thanks for you interest ${contact.name}.<br>Your message has been sent!</div></br>

            `
        } else {
            resultArea.innerHTML = "";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGetContact() {
        let result = await this.client.getContact(this.errorHandler);
        this.dataStore.set("contacts", result);
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        //this.dataStore.set("contact", null);
        let id = "userId";
        let name = document.getElementById("contactname").value;
        let email = document.getElementById("contactemail").value;
        let subject = document.getElementById("contactsubject").value;
        let message = document.getElementById("contactmessage").value;

        const createdContact = await this.client.createContact(id,name,email,subject,message, this.errorHandler);
        this.dataStore.set("contact", createdContact);

        if (createdContact) {
            this.showMessage(`Message sent from ${createdContact.name}!`);
            await this.onGetContact();
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const contactPage = new ContactPage();
    contactPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
