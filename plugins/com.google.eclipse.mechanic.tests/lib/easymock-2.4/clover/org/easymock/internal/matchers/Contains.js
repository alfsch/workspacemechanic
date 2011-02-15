var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 3766, "sl" : 11, "el" : 29, "name" : "Contains",
    "methods" : [
              {"sl" : 17, "el" : 19, "sc" : 5},  {"sl" : 21, "el" : 24, "sc" : 5},  {"sl" : 26, "el" : 28, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_156" : {
					  "name" : "testContains",
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
		"test_401" : {
					  "name" : "constraints",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 26 },],
					  "statements" : [{"sl": 18 },{"sl": 22 },{"sl": 27 },]
					  },
		"test_392" : {
					  "name" : "andOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 22 },]
					  },
		"test_437" : {
					  "name" : "containsToString",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 26 },],
					  "statements" : [{"sl": 18 },{"sl": 27 },]
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
  [ 156 , 437 , 392 , 431 , 401   ] ,
  [ 156 , 437 , 392 , 431 , 401   ] ,
  [  ] ,
  [  ] ,
  [ 156 , 392 , 431 , 401   ] ,
  [ 156 , 392 , 431 , 401   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 437 , 401   ] ,
  [ 437 , 401   ] ,
  [  ] ,
  [  ] 
];
