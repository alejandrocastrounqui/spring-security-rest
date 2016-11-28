#Spring security restful CORS

### This project is an example of how to configure spring security restfully.

#### By default spring has two way to create session

**Basic-autentication:**
```frontend sends authorization header in every request.```
**Cookie based:**
```when frontend sends credentials, Java sets cookie header with session identifier.```
    
The problem is spring security default cookie-based behavior use 302 status to communicate some actions, 
like login success, In this way browser makes redirect and it produces a linkage between view application and the REST api.

In this project you can see how to implement some spring components to allow restful frontend use spring backend as an api.

To facilitate understanding the application flow, each component will be explained below:

**castro.alejandro.security.WebConfiguration**
``` ```


