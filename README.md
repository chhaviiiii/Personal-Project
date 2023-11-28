# ORDER TRACKER APPLICATION

## An efficient tracker for all your business order management needs

- This application will serve as an order tracking system for businesses. The key functionality includes adding new orders, updating the status of orders, and removing completed orders. 

Class X, referred to as **Order**, will contain detailed information about the orders, such as *order ID, product details, customer details, and the current status of the order*.

Class Y, referred to as **OrderTracker**, will maintain a *dynamic list of all active orders*. When an order is fulfilled or completed, the **OrderTracker** will remove it from the active list.


- The primary users of this application will be business owners and their employees who handle order management. It could also be beneficial for restaurant owners. This can include a broad range of businesses - from a small eCommerce store owner selling handmade goods, to a large restaurant chain managing numerous food orders. The application is general enough to suit the needs of any type of business that deals with orders.


- I find this project interesting as it aligns closely with my personal experiences and environment. My brother is a business owner and we currently live in an era where start-ups are prevalent.
Understanding the importance of effective order and task management systems in running a successful business, I see the potential impact this project could have in improving operational efficiencies.  Furthermore, the ability to create a solution that could be beneficial to a wide range of individuals, from students to entrepreneurs. The task-oriented nature of this project resonates with my organizational values, providing an opportunity to innovate in ways that could profoundly impact how people manage their time and workloads.


## User Stories

- As a business owner, I want to add a new order to the tracker, so I can keep track of all active orders.


- As an employee, I want to view all the active orders so that I can process them in a timely manner. 


- As an order manager, I want to update the status of an order to reflect its current progress. 


- As a customer service representative, I want to search for a specific order using the order ID to provide updates or changes to the customer.


- As a startup business owner, I want to be able to load past orders.


- As an employee, I want to change the status of the loaded orders from previous files.


- As a manager, I want to save my orders after creating them.

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by filling out the information on the form (customer name, product name, product type, product price) and clicking on add order.


- You can generate the first required action related to the user story "adding multiple Xs to a Y" by filling out the information on the form (customer name, product name, product type, product price) and clicking on add order.*

If you change the name of the customer then a new order is created, otherwise if the name of the customer is the same then the product is simply added to the existing order
- You can locate my visual component by running the application and a splash screen will be displayed.


- You can save the state of my application by clicking on "File" and pressing Save Orders.


- You can reload the state of my application by clicking on "File" and pressing Load Orders.

# Phase 4: Task 2

Tue Nov 28 15:04:44 PST 2023

Products added to Order: [model.Product@26b9e54e, model.Product@155f1aac]

Tue Nov 28 15:05:02 PST 2023

Order Status updated: DELIVERED

Tue Nov 28 15:05:07 PST 2023

Order found for ID: M43

Tue Nov 28 15:05:10 PST 2023

Orders saved to File

# Phase 4: Task 3

-  The OrderManager class appears to be a central point for various functionalities related to orders. This class could become a maintenance issue as the application grows due to the high number of responsibilities it holds.

Refactoring this class into multiple classes that each handle a single responsibility could improve the design. For instance, breaking down *OrderManager* into **OrderSearchService, OrderCreationService,** and **OrderUpdateService** would make the system more modular and easier to maintain.

- *OrderSearchPanel* and *AddOrderPanel* directly depend on *OrderManager*. Introducing dependency injection could decouple these classes from the concrete implementation of *OrderManager*.

Instead of instantiating an OrderManager inside these panels, the OrderManager could be passed to the constructor or set via a setter method. This would make the system more testable and flexible, allowing for easier substitution of the OrderManager with different implementations or mocks for testing.

- The MainApplicationUI seems is the central hub for the user interface. It is currently handling too many UI elements or actions, it could be refactored into smaller, more focused classes