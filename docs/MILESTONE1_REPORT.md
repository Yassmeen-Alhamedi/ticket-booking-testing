# Software Testing Semester Project Report

## Cover Information

- Course: Software Testing (2010031410)
- Milestone: 1
- Group number:
- Student names and IDs: 
Raghad Hussien Dakhlalala Alsbehat 2338089
Yassmeen Hussien Alhameidi  2241551
Nawal Hamza Thamer Alzagareet  2144005

- Repository URL: https://github.com/Yassmeen-Alhamedi/ticket-booking-testing.git
- Final commit hash: d3b10d20842d2d4ee5e09e21493ad99dab3ff44f 

## 1. System and Test Scope



Milestone 1 implements a `BookingManager` that processes a `BookingRequest` containing a customer ID, an event ID, a ticket quantity, and a price per ticket in cents. A booking is processed in this order: the request is validated, the total amount is calculated (quantity × price), the event is checked for availability, payment is requested, the booking is saved, a confirmation is sent, and the transaction ID is returned.

The payment gateway (`IPaymentGateway`), the notification service (`INotificationService`), and the event repository (`IEventRepository`) are interfaces. In the unit tests they are replaced with Mockito test doubles, so no real payment, messaging, or storage is used.

Intentionally excluded: a GUI, a database, a REST API, authentication, deployment, and any real external integration. Discount calculation is part of Milestone 2 and is not implemented yet.

## 2. Milestone 1: Design and Testability

`BookingManager` receives its three dependencies through its constructor as interfaces and stores them in `final` fields. It never creates a gateway, notification service, or repository itself. In production, real implementations can be passed in; in tests, Mockito mocks are passed in.

This supports testing in two ways. First, we control each dependency's behavior, for example making `isSoldOut` return `true` or making the payment gateway return a failure, without needing the real systems. Second, we can verify the interactions: for example, that `processPayment`, `saveBooking`, and `sendConfirmation` are never called when an event is sold out.

Failures are reported with exceptions, documented in the Javadoc of `bookTicket`:
- `IllegalArgumentException` for a null or invalid request;
- `EventSoldOutException` when the event is sold out;
- `IllegalStateException` when payment fails, meaning the gateway returns a null or blank transaction ID.

Request validation is kept in one place, `BookingManager.validateRequest`, and `BookingRequest` is a plain data holder. This was a design decision made during the first TDD cycle (Section 3): validating in the `BookingRequest` constructor made it impossible to test that `BookingManager` rejects an invalid request before using any dependency.



## 3. Milestone 1: TDD Evidence


### Behavior 1 : Invalid request (M1-US2)

- Red commit: e0c61f0
- Green commit: 119c0ee
- Refactor commit, if applicable: combined with the Green commit
- Short explanation: The Red commit adds a parameterized test for invalid requests. It failed because the BookingRequest constructor threw before BookingManager was reached. The Green commit removed the validation from the BookingRequest constructor so that BookingManager (which already validated) rejects invalid requests before using any dependency.

### Behavior 2  : Sold-out event (M1-US3)


- Red commit:  6c45a76
- Green commit: 8757179
- Refactor commit, if applicable: fd485a0
- Short explanation: The Red commit expects a dedicated EventSoldOutException and failed with "expected EventSoldOutException but was IllegalStateException". The Green commit changes the thrown exception. The Refactor commit extracts calculateTotalAmount and documents the exceptions.

## 4. Limitation of Mocking

A mock only returns what the test tells it to return, so it can hide a mismatch with the real dependency. In our failed-payment test, we stub `processPayment` to return an empty string to represent a failure. A real payment gateway might instead throw an exception or return a different failure value. The test would still pass while the system behaves differently with the real gateway. Mock-based unit tests therefore need to be complemented by contract or integration tests against the real dependency, which are outside the scope of this project.

## 5. Milestone 2: Input Space Model

Identify the characteristics, blocks, and boundaries. Show that the blocks are disjoint and complete.

## 6. Constraints and Infeasible Combinations

Document the required constraints and explain how invalid inputs are handled.

## 7. Pair-Wise Test Set

Present the final feasible rows and briefly justify pair coverage and removed redundancy.

## 8. Limitation of Pair-Wise Testing

Provide one concise, technically accurate limitation.

## 9. Team Contribution Matrix

| Student name and ID | Main responsibilities | Key files or report sections | Representative commit hashes | Contribution confirmed by team |
|---|---|---|---|---|
| Yassmeen Hussein Alhamaeidi - 2241551 | Designed and implemented the BookingRequest class and its initial JUnit tests. Tested valid and invalid booking request data. | BookingRequest.java, BookingRequestTest.java | b96e71a | yes
| Raghad Hussien Alsbehat - 2338089 | Contributed to sold-out event handling , BookingManagerInvalidRequestTest.java,failed-payment tests, booking interfaces, and the event class | BookingManagerSoldOutTest.java, BookingManagerFailedPaymentTest.java, IEventRepository.java,Event.java, IEventRepository.java, INotificationService.java, EventSoldOutException.java IPaymentGateway.java | 57dc1bb, 93b1f31, 7bb5735,119c0ee، 6c45a76، 8757179 fd485a0| yes
| Nawal Hamza Alzagareet - 2144005 | Implemented BookingManager and contributed to the booking logic and successful booking tests. | BookingManager.java, BookingManagerSuccessfulBookingTest.java | 578d63d, b66ea35, b010447 |yes

## Appendix A: AI Use Log

| Date | Tool | Purpose and prompt summary | Files or sections affected | How the output was checked | Decision |
|---|---|---|---|---|---|
| 2026-11-27 | ChatGPT | Assistance with understanding Milestone 1 requirements, discussing the BookingManager implementation, constructor dependency injection, request validation, total amount calculation, and drafting a successful booking test using JUnit 5 and Mockito. | BookingManager.java; BookingManagerSuccessfulBookingTest.java | The suggestions were compared with the official project requirements and the team's interface design. The team must execut

