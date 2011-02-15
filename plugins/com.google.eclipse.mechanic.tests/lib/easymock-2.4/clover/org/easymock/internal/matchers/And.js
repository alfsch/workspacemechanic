var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4171, "sl" : 13, "el" : 42, "name" : "And",
    "methods" : [
              {"sl" : 19, "el" : 21, "sc" : 5},  {"sl" : 23, "el" : 30, "sc" : 5},  {"sl" : 32, "el" : 41, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_376" : {
					  "name" : "testCaptureRightOne",
					  "pass" : true ,
					  "methods" : [{"sl": 19 },{"sl": 23 },],
					  "statements" : [{"sl": 20 },{"sl": 24 },{"sl": 25 },{"sl": 26 },{"sl": 29 },]
					  },
		"test_401" : {
					  "name" : "constraints",
					  "pass" : true ,
					  "methods" : [{"sl": 19 },{"sl": 23 },{"sl": 32 },],
					  "statements" : [{"sl": 20 },{"sl": 24 },{"sl": 25 },{"sl": 26 },{"sl": 29 },{"sl": 33 },{"sl": 34 },{"sl": 35 },{"sl": 36 },{"sl": 37 },{"sl": 40 },]
					  },
		"test_355" : {
					  "name" : "testAnd",
					  "pass" : true ,
					  "methods" : [{"sl": 19 },{"sl": 23 },],
					  "statements" : [{"sl": 20 },{"sl": 24 },{"sl": 25 },{"sl": 29 },]
					  },
		"test_457" : {
					  "name" : "andToString",
					  "pass" : true ,
					  "methods" : [{"sl": 19 },{"sl": 32 },],
					  "statements" : [{"sl": 20 },{"sl": 33 },{"sl": 34 },{"sl": 35 },{"sl": 36 },{"sl": 37 },{"sl": 40 },]
					  },
		"test_392" : {
					  "name" : "andOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 19 },{"sl": 23 },],
					  "statements" : [{"sl": 20 },{"sl": 24 },{"sl": 25 },{"sl": 29 },]
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
  [ 392 , 401 , 355 , 376 , 457   ] ,
  [ 392 , 401 , 355 , 376 , 457   ] ,
  [  ] ,
  [  ] ,
  [ 392 , 401 , 355 , 376   ] ,
  [ 392 , 401 , 355 , 376   ] ,
  [ 392 , 401 , 355 , 376   ] ,
  [ 401 , 376   ] ,
  [  ] ,
  [  ] ,
  [ 392 , 401 , 355 , 376   ] ,
  [  ] ,
  [  ] ,
  [ 401 , 457   ] ,
  [ 401 , 457   ] ,
  [ 401 , 457   ] ,
  [ 401 , 457   ] ,
  [ 401 , 457   ] ,
  [ 401 , 457   ] ,
  [  ] ,
  [  ] ,
  [ 401 , 457   ] ,
  [  ] ,
  [  ] 
];
