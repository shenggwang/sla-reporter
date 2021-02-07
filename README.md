# A simple server to report SLA

The required Architecture Principles:
- Re-usable Architect.
- Scalable based on incoming load.
- Data security.
- Performance and cost in mind for database access.
- Design with failure in mind.

## Implementation

The application will contain only a single public API that is used to create subscriptions.
Subscription contains: 
- email
- firstName (Optional)
- gender (Optional)
- dateOfBirth
- a flag for consent
- the newsletter ID (corresponding to the campaign)

The application might:
- a thread pool to handle database connection.
- be able to send email: _javax.mail_.
- execute cronjob: _Quartz Scheduler_.

Components (details):
- Nginx: for HTTPS request control and blue-green deploying (by swapping the request to another internal application)
- Elasticsearch: for vertical scale, data availability and index-based search.
- Docker: for microservices.
- Kubernetes: for controlling Jenkins (CI CD).
