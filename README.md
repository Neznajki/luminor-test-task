
# if you will inspect there will be some additional functioanlity like
## I made those decisions only as test task part to negate spending time. in real tasks I would start to ask those questions to some one to prevent me from making technical decisions, they shuold be made by product owner not the developer.
* min payment limitation
* registration of clients
* login of client
* error in case when payment canceled doesn't have enought funds to consume cancel fee.

# estimates (includes info gathering, does not fully include Architecture design)
* creating db structure 1.5h
* creating basic dockerfile 2h
* creating entity fixing encoding finishing tables 1h
* creating authorization logic 2h
* configuring tests 1h
* implementing first set of tests 5h
* implemented ip gathering by request interceptor (fixed database structure) 1h
* implemented country ip gathering 2h (no unit tests)
* implemented country ip gathering test coverage 5h
* implemented payment create rest api method 6h (including all related validations and microservice notification)
* implemented GUI add payment 2h
* implemented simple table preview basic filters. 6h
* totals 34 hours 30 minutes
# all requirements is match (more or less)

# api user pass 
* user: RESTapi
* pass: api_pass

# api example
* get canceled payment by amount filter max
```shell script
curl --location --request GET 'http://localhost:8080/rest-api/canceled/payments' \
--header 'Connection: keep-alive' \
--header 'Pragma: no-cache' \
--header 'Cache-Control: no-cache' \
--header 'Accept: */*' \
--header 'X-Requested-With: XMLHttpRequest' \
--header 'Authorization: Basic UkVTVGFwaTphcGlfcGFzcw==' \
--header 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/83.0.4103.61 Chrome/83.0.4103.61 Safari/537.36' \
--header 'Content-Type: application/json' \
--header 'Origin: http://localhost:8080' \
--header 'Sec-Fetch-Site: same-origin' \
--header 'Sec-Fetch-Mode: cors' \
--header 'Sec-Fetch-Dest: empty' \
--header 'Referer: http://localhost:8080/' \
--header 'Accept-Language: en-US,en;q=0.9,ru;q=0.8' \
--header 'Cookie: JSESSIONID=BEA12034CA97BC688E0E1B849DC482AE; JSESSIONID=D451568F064A75A3E3AF1120D678A3CE' \
--data-raw '{"max":50}'
```

* get canceled payment by amount filter min max
```shell script
curl --location --request GET 'http://localhost:8080/rest-api/canceled/payments' \
--header 'Connection: keep-alive' \
--header 'Pragma: no-cache' \
--header 'Cache-Control: no-cache' \
--header 'Accept: */*' \
--header 'X-Requested-With: XMLHttpRequest' \
--header 'Authorization: Basic UkVTVGFwaTphcGlfcGFzcw==' \
--header 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/83.0.4103.61 Chrome/83.0.4103.61 Safari/537.36' \
--header 'Content-Type: application/json' \
--header 'Origin: http://localhost:8080' \
--header 'Sec-Fetch-Site: same-origin' \
--header 'Sec-Fetch-Mode: cors' \
--header 'Sec-Fetch-Dest: empty' \
--header 'Referer: http://localhost:8080/' \
--header 'Accept-Language: en-US,en;q=0.9,ru;q=0.8' \
--header 'Cookie: JSESSIONID=BEA12034CA97BC688E0E1B849DC482AE; JSESSIONID=D451568F064A75A3E3AF1120D678A3CE' \
--data-raw '{"max":50, "max":500}'
```

* search by id
```shell script
curl --location --request GET 'http://localhost:8080/rest-api/get/by/uuid' \
--header 'Connection: keep-alive' \
--header 'Pragma: no-cache' \
--header 'Cache-Control: no-cache' \
--header 'Accept: */*' \
--header 'X-Requested-With: XMLHttpRequest' \
--header 'Authorization: Basic UkVTVGFwaTphcGlfcGFzcw==' \
--header 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/83.0.4103.61 Chrome/83.0.4103.61 Safari/537.36' \
--header 'Content-Type: application/json' \
--header 'Origin: http://localhost:8080' \
--header 'Sec-Fetch-Site: same-origin' \
--header 'Sec-Fetch-Mode: cors' \
--header 'Sec-Fetch-Dest: empty' \
--header 'Referer: http://localhost:8080/' \
--header 'Accept-Language: en-US,en;q=0.9,ru;q=0.8' \
--header 'Cookie: JSESSIONID=BEA12034CA97BC688E0E1B849DC482AE; JSESSIONID=D451568F064A75A3E3AF1120D678A3CE' \
--data-raw '{"id":"dcc711cd-63c7-4fc9-9658-a1efa1bb9523"}'
```

# why ?
* why protected method not private // just to mock them and simplify unit tests (if there would be private methods many tests would end up as messy large method) || PS it probably not okay for real code to change visibility for better tests split, but in case for code review it could be useful.

# possible improvements
* currency realtime update // background
* good GUI grid
* security roles system improvement
* make full functional GUI
* cover 100% code with tests.
* logging // errors infos and warnings
* add retry on external micro service fails
* add batching to external services
* improve timings (before payment handling and payment add there are n milliseconds or even seconds on db load. it could be eliminated by adding time on request receive.)
* fail in case ping is more than N seconds (sending responding)
* add socket connection to merge client + server time.
* realtime data update.
* implement response status code changes on errors
* detect reason why @transactional flag still creates database rows!

