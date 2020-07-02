# Native Android App + PHP Admin Panel & API

### 1. Native Android Application

The application is developed in *Java* using *Android Studio*.

To use the application, users have to register.
There are two types of accounts: Full and Partial. Full accounts will be asked to pay a monthly fee, but will have access to more content.
Payments are virtually handled by a Braintree (PayPal) sandbox.

![](https://www.sedhna.com/rovers/screens.png)

### 2. Admin Panel

The admin panel allows an admin user to update the MySQL database. The admin can add, remove and amend records.

The admin panel is developed using *HTML*, *CSS*, *JavaScript*, *PHP* and *SQL*. It makes extensive use of bootstrap.

![](https://www.sedhna.com/rovers/screens2.png)

### 3. API

The API allows for users registration and authentication and also provides content to the native application.
It returns the databse content as a JSON string.

The API is implemented using [Slim PHP framework](https://www.slimframework.com).

![](https://www.sedhna.com/rovers/screens3.png)
