var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2465, "sl" : 11, "el" : 30, "name" : "Not",
    "methods" : [
              {"sl" : 17, "el" : 19, "sc" : 5},  {"sl" : 21, "el" : 23, "sc" : 5},  {"sl" : 25, "el" : 29, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_145" : {
					  "name" : "testNotNull",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 22 },]
					  },
		"test_25" : {
					  "name" : "testNull",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 22 },]
					  },
		"test_431" : {
					  "name" : "notOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 22 },]
					  },
		"test_373" : {
					  "name" : "equalsMissing",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },],
					  "statements" : [{"sl": 18 },]
					  },
		"test_456" : {
					  "name" : "notToString",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 25 },],
					  "statements" : [{"sl": 18 },{"sl": 26 },{"sl": 27 },{"sl": 28 },]
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
  [ 373 , 431 , 145 , 25 , 456   ] ,
  [ 373 , 431 , 145 , 25 , 456   ] ,
  [  ] ,
  [  ] ,
  [ 431 , 145 , 25   ] ,
  [ 431 , 145 , 25   ] ,
  [  ] ,
  [  ] ,
  [ 456   ] ,
  [ 456   ] ,
  [ 456   ] ,
  [ 456   ] ,
  [  ] ,
  [  ] 
];
