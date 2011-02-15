var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2341, "sl" : 15, "el" : 56, "name" : "LegacyMatcherProvider",
    "methods" : [
              {"sl" : 23, "el" : 31, "sc" : 5},  {"sl" : 34, "el" : 42, "sc" : 5},  {"sl" : 44, "el" : 55, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_233" : {
					  "name" : "expectedArgumentsDelegatedToMatcher",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 34 },{"sl": 44 },],
					  "statements" : [{"sl": 24 },{"sl": 25 },{"sl": 26 },{"sl": 28 },{"sl": 30 },{"sl": 35 },{"sl": 40 },{"sl": 41 },{"sl": 45 },{"sl": 54 },]
					  },
		"test_289" : {
					  "name" : "defaultMatcher",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 34 },],
					  "statements" : [{"sl": 24 },{"sl": 25 },{"sl": 28 },{"sl": 30 },{"sl": 35 },{"sl": 40 },{"sl": 41 },]
					  },
		"test_375" : {
					  "name" : "setMatcherTwice2",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },],
					  "statements" : [{"sl": 45 },{"sl": 46 },{"sl": 54 },]
					  },
		"test_142" : {
					  "name" : "unexpectedCallWithArray",
					  "pass" : true ,
					  "methods" : [{"sl": 34 },],
					  "statements" : [{"sl": 35 },{"sl": 40 },{"sl": 41 },]
					  },
		"test_251" : {
					  "name" : "settingTheSameMatcherIsOk",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 44 },],
					  "statements" : [{"sl": 24 },{"sl": 30 },{"sl": 45 },{"sl": 54 },]
					  },
		"test_185" : {
					  "name" : "message",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 44 },],
					  "statements" : [{"sl": 24 },{"sl": 30 },{"sl": 45 },{"sl": 54 },]
					  },
		"test_377" : {
					  "name" : "setMatcherTwice",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },],
					  "statements" : [{"sl": 45 },{"sl": 46 },{"sl": 50 },{"sl": 54 },]
					  },
		"test_424" : {
					  "name" : "failIfDefaultMatcherSetTwice",
					  "pass" : true ,
					  "methods" : [{"sl": 34 },],
					  "statements" : [{"sl": 35 },{"sl": 36 },{"sl": 40 },{"sl": 41 },]
					  },
		"test_237" : {
					  "name" : "expectedArgumentsDelegatedToMatcher2",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 44 },],
					  "statements" : [{"sl": 24 },{"sl": 30 },{"sl": 45 },{"sl": 54 },]
					  },
		"test_433" : {
					  "name" : "defaultMatcherSetTooLate",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 34 },],
					  "statements" : [{"sl": 24 },{"sl": 25 },{"sl": 28 },{"sl": 30 },{"sl": 35 },{"sl": 40 },{"sl": 41 },]
					  },
		"test_269" : {
					  "name" : "unexpectedCallWithArray",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 34 },],
					  "statements" : [{"sl": 24 },{"sl": 25 },{"sl": 28 },{"sl": 30 },{"sl": 35 },{"sl": 40 },{"sl": 41 },]
					  },
		"test_248" : {
					  "name" : "errorString",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 44 },],
					  "statements" : [{"sl": 24 },{"sl": 30 },{"sl": 45 },{"sl": 54 },]
					  },
		"test_391" : {
					  "name" : "setSameMatcherTwice",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },],
					  "statements" : [{"sl": 45 },{"sl": 54 },]
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
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 233 , 289 , 433 , 248 , 237 , 185 , 269 , 251   ] ,
  [ 233 , 289 , 433 , 248 , 237 , 185 , 269 , 251   ] ,
  [ 233 , 289 , 433 , 269   ] ,
  [ 233   ] ,
  [  ] ,
  [ 233 , 289 , 433 , 269   ] ,
  [  ] ,
  [ 233 , 289 , 433 , 248 , 237 , 185 , 269 , 251   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 424 , 233 , 289 , 433 , 142 , 269   ] ,
  [ 424 , 233 , 289 , 433 , 142 , 269   ] ,
  [ 424   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 424 , 233 , 289 , 433 , 142 , 269   ] ,
  [ 424 , 233 , 289 , 433 , 142 , 269   ] ,
  [  ] ,
  [  ] ,
  [ 391 , 233 , 377 , 248 , 237 , 185 , 251 , 375   ] ,
  [ 391 , 233 , 377 , 248 , 237 , 185 , 251 , 375   ] ,
  [ 377 , 375   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 377   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 391 , 233 , 377 , 248 , 237 , 185 , 251 , 375   ] ,
  [  ] ,
  [  ] 
];
