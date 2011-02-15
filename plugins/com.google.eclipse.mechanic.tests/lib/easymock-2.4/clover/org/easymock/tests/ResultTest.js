var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2333, "sl" : 9, "el" : 24, "name" : "ResultTest",
    "methods" : [
              {"sl" : 11, "el" : 16, "sc" : 5},  {"sl" : 18, "el" : 23, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_239" : {
					  "name" : "createThrowResultToString",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 13 },{"sl": 14 },{"sl": 15 },]
					  },
		"test_225" : {
					  "name" : "createReturnResultToString",
					  "pass" : true ,
					  "methods" : [{"sl": 18 },],
					  "statements" : [{"sl": 20 },{"sl": 21 },{"sl": 22 },]
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
  [ 239   ] ,
  [  ] ,
  [ 239   ] ,
  [ 239   ] ,
  [ 239   ] ,
  [  ] ,
  [  ] ,
  [ 225   ] ,
  [  ] ,
  [ 225   ] ,
  [ 225   ] ,
  [ 225   ] ,
  [  ] ,
  [  ] 
];
