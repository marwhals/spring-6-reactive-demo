# Reactive Manifesto - Spring Boot notes

## Goals
- Responsive
- Driven
- Elastic
- Message Driven

[//]: # (Add notes as needed)

*Best used for streaming type applications ¯\_(ツ)_/¯*
*Immutable nature of Reactive applications can help with application quality*

# Important ‼
- *Backpressure* this ability of the subscriber to throttle data

# Failures as Messages
- Exceptions are not thrown in a traditional sense
- Would break processing of stream
- Exceptions are processed by a handler function

### Note: Use Netty not Tomcat
[//]: # (TODO Research)