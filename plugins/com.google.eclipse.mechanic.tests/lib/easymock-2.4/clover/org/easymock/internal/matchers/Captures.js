var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 113, "sl" : 12, "el" : 31, "name" : "Captures",
    "methods" : [
              {"sl" : 18, "el" : 20, "sc" : 5},  {"sl" : 22, "el" : 24, "sc" : 5},  {"sl" : 26, "el" : 30, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_361" : {
					  "name" : "testPrimitive",
					  "pass" : true ,
					  "methods" : [{"sl": 18 },{"sl": 26 },],
					  "statements" : [{"sl": 19 },{"sl": 28 },{"sl": 29 },]
					  },
		"test_376" : {
					  "name" : "testCaptureRightOne",
					  "pass" : true ,
					  "methods" : [{"sl": 18 },{"sl": 26 },],
					  "statements" : [{"sl": 19 },{"sl": 28 },{"sl": 29 },]
					  },
		"test_382" : {
					  "name" : "testPrimitiveVsObject",
					  "pass" : true ,
					  "methods" : [{"sl": 18 },{"sl": 26 },],
					  "statements" : [{"sl": 19 },{"sl": 28 },{"sl": 29 },]
					  },
		"test_355" : {
					  "name" : "testAnd",
					  "pass" : true ,
					  "methods" : [{"sl": 18 },{"sl": 26 },],
					  "statements" : [{"sl": 19 },{"sl": 28 },{"sl": 29 },]
					  },
		"test_421" : {
					  "name" : "test",
					  "pass" : true ,
					  "methods" : [{"sl": 22 },{"sl": 26 },],
					  "statements" : [{"sl": 23 },{"sl": 28 },{"sl": 29 },]
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
  [ 355 , 376 , 382 , 361   ] ,
  [ 355 , 376 , 382 , 361   ] ,
  [  ] ,
  [  ] ,
  [ 421   ] ,
  [ 421   ] ,
  [  ] ,
  [  ] ,
  [ 355 , 376 , 382 , 421 , 361   ] ,
  [  ] ,
  [ 355 , 376 , 382 , 421 , 361   ] ,
  [ 355 , 376 , 382 , 421 , 361   ] ,
  [  ] ,
  [  ] 
];
