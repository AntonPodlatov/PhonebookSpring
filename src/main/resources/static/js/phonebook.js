function Contact(firstName, lastName, phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.checked = false;
    this.shown = true;
}

Vue.use(VueMask.VueMaskPlugin);

new Vue({
    el: "#app",

    data: {
        api: "/phonebook/rest/api/v1",
        validation: false,
        firstName: "",
        lastName: "",
        phone: "",
        rows: [],
        filterValue: "",
        isAllChecked: false,
        contactsForRemove: [],
        warningsList: [],
        detailedContact: {}
    },

    methods: {
        checkAllCheckboxes() {
            let isAllChecked = this.isAllChecked;
            this.rows.forEach(row => row.checked = isAllChecked);
        },

        contactToString(contact) {
            return contact.firstName + " " + contact.lastName + ", " + contact.phone;
        },

        convertContactList(contactListFromServer) {
            return contactListFromServer.map((contact, i) => ({
                id: contact.id,
                firstName: contact.firstName,
                lastName: contact.lastName,
                phone: contact.phone,
                checked: false,
                shown: true,
                number: this.rows.length + i + 1,
            }));
        },

        addContact() {
            if (this.hasError) {
                this.validation = true;
                return;
            }

            var contact = new Contact(this.firstName, this.lastName, this.phone);

            $.ajax({
                type: "POST",
                url: `${this.api}/contacts`,
                contentType: "application/json",
                data: JSON.stringify(contact)
            }).done(newContact => {
                this.rows.push(...this.convertContactList([newContact]));
            }).fail(ajaxRequest => {
                this.showWarnings(ajaxRequest.responseJSON.messages)
            }).always();

            this.firstName = "";
            this.lastName = "";
            this.phone = "";
            this.validation = false;
        },

        loadData(applyFilter = false) {
            var path = `${this.api}/contacts`;

            if (applyFilter) {
                path += "?filter=" + this.filterValue;
            }

            $.get(path).done(response =>
                this.rows = this.convertContactList(response));
        },

        filter() {
            this.loadData(true);
        },

        resetFilter() {
            this.filterValue = "";
            this.loadData();
        },

        remove() {
            $.ajax({
                type: 'DELETE',
                url: `${this.api}/contacts` + "?" + "ids=" + this.contactsForRemove.map(checkedRow => checkedRow.id)
            }).done(response => {
                if (response.message) {
                    this.showWarnings([response.message]);
                }
                this.rows = this.rows.filter(row => !response.removedContactsIds.includes(row.id));
            }).fail(response => {
                if (response.responseJSON.messages) {
                    this.showWarnings(response.responseJSON.messages);
                }
            }).always(() => this.contactsForRemove = [])
        },

        showRemovingDialog(row = null) {
            if (row === null) {
                var checkedRows = this.rows.filter(function (row) {
                    return row.checked === true;
                });

                if (checkedRows.length === 0) {
                    this.showWarnings(["Не выбрано ни одного контакта"]);
                    return;
                }

                this.contactsForRemove = checkedRows;
            } else {
                this.contactsForRemove = [row];
            }

            var modal = new bootstrap.Modal(this.$refs.modalWindow, {
                backdrop: true,
                keyboard: true,
                focus: true
            });

            modal.show();
        },

        showWarnings(warningsList) {
            this.warningsList = warningsList;

            var warning = new bootstrap.Modal(this.$refs.warningWindow, {
                backdrop: true,
                keyboard: true,
                focus: true
            });

            warning.show();
        },

        clearWarningsList() {
            this.warningsList = [];
        },

        closeAndUpdate() {
            this.loadData(false);
            this.clearWarningsList();
        },

        downloadAllContactsXlsx() {
            if (this.rows.length < 1) {
                this.showWarnings(["Контактов нет."]);
                return;
            }
            window.location.href = this.api + "/xlsx/contacts";
        },

        convertTimeFromSeconds(secondsCount) {
            return new Date(secondsCount * 1000).toISOString().slice(11, 19);
        },

        showContactDetailed(row) {
            if (window.getSelection().toString()) {
                return;
            }

            var path = `${this.api}/contacts/${row.id}`;

            $.get(path)
                .done(detailedContact => {
                    console.log(detailedContact);
                    this.detailedContact = detailedContact;

                    var contactDetailedWindow = new bootstrap.Modal(
                        this.$refs.contactDetailedWindow, {
                            backdrop: true,
                            keyboard: true,
                            focus: true
                        }
                    );
                    contactDetailedWindow.show();
                }).fail(ajaxRequest => this.showWarnings(ajaxRequest.responseJSON.messages))
                .always();
        }
    },

    computed: {
        contactAlreadyExists() {
            return this.rows.some(c => c.phone === this.phone);
        },

        firstNameError() {
            return this.firstName ? {
                message: "",
                error: false
            } : {
                message: "Поле Имя должно быть заполнено.",
                error: true
            };
        },

        lastNameError() {
            return !this.lastName ? {
                message: "Поле Фамилия должно быть заполнено.",
                error: true
            } : {
                message: "",
                error: false
            };
        },

        phoneError() {
            return !this.phone ? {
                message: "Поле Телефон должно быть заполнено.",
                error: true
            } : this.contactAlreadyExists ? {
                message: "Номер телефона не должен дублировать другие номера в телефонной книге.",
                error: true
            } : {
                message: "",
                error: false
            };
        },

        hasError() {
            return this.lastNameError.error || this.firstNameError.error || this.phoneError.error;
        }
    },

    created() {
        this.loadData();
    }
});