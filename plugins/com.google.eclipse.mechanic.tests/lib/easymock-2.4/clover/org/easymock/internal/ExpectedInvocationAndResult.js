var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 258, "sl" : 9, "el" : 30, "name" : "ExpectedInvocationAndResult",
    "methods" : [
              {"sl" : 17, "el" : 21, "sc" : 5},  {"sl" : 23, "el" : 25, "sc" : 5},  {"sl" : 27, "el" : 29, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "greaterOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_242" : {
					  "name" : "returnInt",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_145" : {
					  "name" : "testNotNull",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_177" : {
					  "name" : "testSame",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_26" : {
					  "name" : "doubleType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_21" : {
					  "name" : "objectType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_9" : {
					  "name" : "floatType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_381" : {
					  "name" : "stubBehavior",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_299" : {
					  "name" : "throwableAndDefaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_143" : {
					  "name" : "testFind",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_285" : {
					  "name" : "doubleType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_235" : {
					  "name" : "returnLong",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_268" : {
					  "name" : "defaultVoidCallable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_283" : {
					  "name" : "object",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_378" : {
					  "name" : "overloading",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_156" : {
					  "name" : "testContains",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_278" : {
					  "name" : "defaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_50" : {
					  "name" : "returnShort",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_296" : {
					  "name" : "defaultBehavior",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_238" : {
					  "name" : "returnChar",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_164" : {
					  "name" : "equalsWithDelta",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_273" : {
					  "name" : "defaultReturnValueBoolean",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_6" : {
					  "name" : "greaterThan",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_8" : {
					  "name" : "lessOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_294" : {
					  "name" : "floatType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_10" : {
					  "name" : "lessThan",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_440" : {
					  "name" : "returnFloat",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_19" : {
					  "name" : "testOr",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_54" : {
					  "name" : "returnByte",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_275" : {
					  "name" : "stubWithReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_220" : {
					  "name" : "testGenericityFlexibility",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_16" : {
					  "name" : "booleanType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_460" : {
					  "name" : "returnDouble",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_328" : {
					  "name" : "booleanType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_25" : {
					  "name" : "testNull",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_136" : {
					  "name" : "testMatches",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_266" : {
					  "name" : "defaultReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_149" : {
					  "name" : "testStartsWith",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_12" : {
					  "name" : "longType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_280" : {
					  "name" : "stub",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_210" : {
					  "name" : "stubAnswer",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_325" : {
					  "name" : "longType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_403" : {
					  "name" : "defaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_169" : {
					  "name" : "testEndsWith",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_290" : {
					  "name" : "returnValueAndDefaultReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  }
 };

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [  [],   [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 54 , 50 , 16 , 273 , 21 , 19 , 25 , 440 , 10 , 242 , 177 , 220 , 381 , 280 , 266 , 275 , 6 , 296 , 210 , 283 , 299 , 26 , 460 , 169 , 378 , 156 , 290 , 12 , 3 , 268 , 328 , 403 , 149 , 164 , 145 , 9 , 238 , 285 , 8 , 143 , 294 , 136 , 278 , 325 , 235   ] ,
  [  ] ,
  [ 54 , 50 , 16 , 273 , 21 , 19 , 25 , 440 , 10 , 242 , 177 , 220 , 381 , 280 , 266 , 275 , 6 , 296 , 210 , 283 , 299 , 26 , 460 , 169 , 378 , 156 , 290 , 12 , 3 , 268 , 328 , 403 , 149 , 164 , 145 , 9 , 238 , 285 , 8 , 143 , 294 , 136 , 278 , 325 , 235   ] ,
  [ 54 , 50 , 16 , 273 , 21 , 19 , 25 , 440 , 10 , 242 , 177 , 220 , 381 , 280 , 266 , 275 , 6 , 296 , 210 , 283 , 299 , 26 , 460 , 169 , 378 , 156 , 290 , 12 , 3 , 268 , 328 , 403 , 149 , 164 , 145 , 9 , 238 , 285 , 8 , 143 , 294 , 136 , 278 , 325 , 235   ] ,
  [  ] ,
  [  ] ,
  [ 54 , 50 , 16 , 273 , 21 , 19 , 25 , 440 , 10 , 242 , 177 , 220 , 381 , 280 , 266 , 275 , 6 , 296 , 210 , 283 , 299 , 26 , 460 , 169 , 378 , 156 , 290 , 12 , 3 , 268 , 328 , 403 , 149 , 164 , 145 , 9 , 238 , 285 , 8 , 143 , 294 , 136 , 278 , 325 , 235   ] ,
  [ 54 , 50 , 16 , 273 , 21 , 19 , 25 , 440 , 10 , 242 , 177 , 220 , 381 , 280 , 266 , 275 , 6 , 296 , 210 , 283 , 299 , 26 , 460 , 169 , 378 , 156 , 290 , 12 , 3 , 268 , 328 , 403 , 149 , 164 , 145 , 9 , 238 , 285 , 8 , 143 , 294 , 136 , 278 , 325 , 235   ] ,
  [  ] ,
  [  ] ,
  [ 54 , 50 , 16 , 273 , 21 , 19 , 25 , 440 , 10 , 242 , 177 , 220 , 381 , 280 , 266 , 275 , 6 , 296 , 210 , 283 , 299 , 26 , 460 , 169 , 378 , 156 , 290 , 12 , 3 , 268 , 328 , 403 , 149 , 164 , 145 , 9 , 238 , 285 , 8 , 143 , 294 , 136 , 278 , 325 , 235   ] ,
  [ 54 , 50 , 16 , 273 , 21 , 19 , 25 , 440 , 10 , 242 , 177 , 220 , 381 , 280 , 266 , 275 , 6 , 296 , 210 , 283 , 299 , 26 , 460 , 169 , 378 , 156 , 290 , 12 , 3 , 268 , 328 , 403 , 149 , 164 , 145 , 9 , 238 , 285 , 8 , 143 , 294 , 136 , 278 , 325 , 235   ] ,
  [  ] ,
  [  ] 
];
