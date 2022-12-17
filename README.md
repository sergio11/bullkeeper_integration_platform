# BullKeeper Integration Platform

BullKeeper Integration Platform. EIPs patterns with Spring Integration

Backend that supports the functionality of the applications:

* [Bullkeeper (Android App - Parental control tool focused on the management of the digital lifestyle of children/adolescents)](https://github.com/sergio11/bullkeeper_app) 
* [BullKeeper Kids Android App - Tool aimed at children/adolescents to manage their fun time and ensure the rules of parental control set.](https://github.com/sergio11/bullkeeper_kids_app) 

The platform offers interoperability with other systems, allows information from social media, users under analysis, parents, schools. It can be updated, deleted, consulted and saved. These services are not freely accessible, the parent/guardian must prove her identity and obtain an access token to the API.
The system will only allow you to check and manipulate the information of the tutored children.

Platform structured in four clearly differentiated components:

1. **ETL process** (Extract, Transform and Load):

Component developed with the objective of extracting and consolidating the comments and / or opinions published by the users of the analysis (children / adolescents) in the different social media enabled by the parent / guardian through the mobile application.
This component has been raised under the spectrum of business integration patterns (EIP), whose reference implementation is Spring Integration.
The system is integrated with different platforms to obtain the resources related to each social environment in an incremental way, homogenizing that information giving it a structure that facilitates its treatment and then consolidates it for a later analysis.
In regular periods of time it obtains the configured social media that are valid (they have a valid session "token") assigned to each of the users that are the object of the analysis.
The extraction of information for that social environment will be treated as an independent task belonging to the current iteration and will adequately control any error that occurred during the execution of the task as well as recording the corresponding alerts generated for a
Subsequent delivery to the group of notification devices of the tutor / parent through the notification delivery component.
Depending on the task load the system will decide how to distribute these in different iteration cycles to avoid an overload of the system.

2. **Hypermedia RESTful API**:

Provides interoperability with other systems and applications. Facilitates access / manipulation of system information. Most web services are protected with sophisticated security rules to prevent 
unauthorized users from accessing certain information that does not correspond to them.

3. **SSE Event Engine**:

It will maintain open connections with mobile applications for short periods of time in order to send updated information. It will be the main mechanism for the synchronization of parental control rules and other settings.

4. **Multi-device Alert Notification System**:

The system will be integrated with the Firebase push notification service called FCM, with the aim of reporting accurately and quickly on certain events and alerts that have been carried out with supervised children / adolescents.


![Bullkeeper Arquitecture](bullkeeper_arquitecture.png "Bullkeeper Arquitecture")

## Visitors Count

<img width="auto" src="https://profile-counter.glitch.me/bullkeeper_integration_platform/count.svg" />

## Please Share & Star the repository to keep me motivated.
  <a href = "https://github.com/sergio11/bullkeeper_integration_platform/stargazers">
     <img src = "https://img.shields.io/github/stars/sergio11/bullkeeper_integration_platform" />
  </a>
  <a href = "https://twitter.com/SergioReact418">
     <img src = "https://img.shields.io/twitter/url?label=follow&style=social&url=https%3A%2F%2Ftwitter.com%2FSergioReact418" />
  </a>

