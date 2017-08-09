1、 get token [POST]

    http://localhost:8080/oauth/token
    
    request parameters:
    client_test = test
    client_secret = test
    scope = read write
    username = test
    password = test
    grant_type = password
    
    response:
    {"access_token":"eec11962d02a6a474e06bd381feebef9","refresh_token":"f41c813264fd006c0b14f17148c59ccc","token_type":"Bearer","expires_in":43199}
    
2、refresh token [POST]
 
    http://localhost:8080/oauth/token
    
    request parameters:
    client_id=test
    client_secret=test
    grant_type=refresh_token
    refresh_token=b36f4978a1724aa8af8960f58abe3ba1
    
3、resource token

    http://localhost:8080/rs/username
    
    request parameters:
    access_token=6b7b0726e603cd04c797d5b28c7be4c4
    
4、api documents

    http://lcaolhost:8080/swagger/index.html