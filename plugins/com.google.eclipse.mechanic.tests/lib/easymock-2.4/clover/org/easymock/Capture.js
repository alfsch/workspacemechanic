var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2202, "sl" : 15, "el" : 67, "name" : "Capture",
    "methods" : [
              {"sl" : 26, "el" : 29, "sc" : 5},  {"sl" : 34, "el" : 36, "sc" : 5},  {"sl" : 44, "el" : 49, "sc" : 5},  {"sl" : 55, "el" : 58, "sc" : 5},  {"sl" : 60, "el" : 66, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_361" : {
					  "name" : "testPrimitive",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },{"sl": 55 },],
					  "statements" : [{"sl": 45 },{"sl": 48 },{"sl": 56 },{"sl": 57 },]
					  },
		"test_376" : {
					  "name" : "testCaptureRightOne",
					  "pass" : true ,
					  "methods" : [{"sl": 34 },{"sl": 44 },{"sl": 55 },],
					  "statements" : [{"sl": 35 },{"sl": 45 },{"sl": 48 },{"sl": 56 },{"sl": 57 },]
					  },
		"test_198" : {
					  "name" : "testCapture",
					  "pass" : true ,
					  "methods" : [{"sl": 26 },{"sl": 34 },{"sl": 44 },{"sl": 55 },{"sl": 60 },],
					  "statements" : [{"sl": 27 },{"sl": 28 },{"sl": 35 },{"sl": 45 },{"sl": 46 },{"sl": 48 },{"sl": 56 },{"sl": 57 },{"sl": 62 },{"sl": 63 },{"sl": 65 },]
					  },
		"test_382" : {
					  "name" : "testPrimitiveVsObject",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },{"sl": 55 },],
					  "statements" : [{"sl": 45 },{"sl": 48 },{"sl": 56 },{"sl": 57 },]
					  },
		"test_355" : {
					  "name" : "testAnd",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },{"sl": 55 },],
					  "statements" : [{"sl": 45 },{"sl": 48 },{"sl": 56 },{"sl": 57 },]
					  },
		"test_421" : {
					  "name" : "test",
					  "pass" : true ,
					  "methods" : [{"sl": 55 },{"sl": 60 },],
					  "statements" : [{"sl": 56 },{"sl": 57 },{"sl": 62 },{"sl": 63 },{"sl": 65 },]
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
  [  ] ,
  [  ] ,
  [  ] ,
  [ 198   ] ,
  [ 198   ] ,
  [ 198   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 376 , 198   ] ,
  [ 376 , 198   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 355 , 376 , 382 , 198 , 361   ] ,
  [ 355 , 376 , 382 , 198 , 361   ] ,
  [ 198   ] ,
  [  ] ,
  [ 355 , 376 , 382 , 198 , 361   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 355 , 421 , 376 , 382 , 198 , 361   ] ,
  [ 355 , 421 , 376 , 382 , 198 , 361   ] ,
  [ 355 , 421 , 376 , 382 , 198 , 361   ] ,
  [  ] ,
  [  ] ,
  [ 421 , 198   ] ,
  [  ] ,
  [ 421 , 198   ] ,
  [ 421 , 198   ] ,
  [  ] ,
  [ 421 , 198   ] ,
  [  ] ,
  [  ] 
];
