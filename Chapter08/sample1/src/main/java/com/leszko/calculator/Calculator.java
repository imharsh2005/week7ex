package com.leszko.calculator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/** Calculator logic */
@Service
public class Calculator {
	@Cacheable("sum")
	public int sum(int a, int b) {
		return a + b;
	}

        @Cacheable("div")
        public int div(int a, int b) {
                return a / b;
        }
}

// package com.leszko.calculator;

// import org.springframework.cache.annotation.Cacheable;
// import org.springframework.stereotype.Service;

// /**
// * @author Some javadoc. // OK
// */
// @Service
// public class Calculator {
//         final static int umlNUMBER1 = 3;
// 	@Cacheable("sum")
// 	public int sum(int a, int b) {
// 		return a + b;
// 	}
// }
