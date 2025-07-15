# test-exchangefilterfunction

Test Cases

1. Call Without Header

    curl http://localhost:8080/test-call

    Expected Result:
    A JSON response from https://httpbin.org/get

    The header X-Test-Env should not appear in the headers section

2. Call With Header
    curl -H "X-Test-Env: staging" http://localhost:8080/test-call

    Expected Result:
    A JSON response from https://httpbin.org/get

    The X-Test-Env: staging header should appear under headers
    {
        "headers": {
            "Accept": "*/*",
            "Host": "httpbin.org",
            "User-Agent": "ReactorNetty/1.1.11",
            "X-Test-Env": "staging"
        },
        ...
    }