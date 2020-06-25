# estimates
* creating db structure 1.5h
* creating basic dockerfile 2h
* creating entity fixing encoding finishing tables 1h
* creating authorization logic 2h
* configuring tests 1h
* implementing first set of tests 5h
* implemented ip gathering by request interceptor (fixed database structure) 1h
* implemented country ip gathering 2h (no unit tests)
* implemented country ip gathering test coverage 4h

# why ?
* why protected method not private // just to mock them and simplify unit tests (if there would be private methods many tests would end up as messy large method) || PS it probably not okay for real code to change visibility for better tests split, but in case for code review it could be useful.