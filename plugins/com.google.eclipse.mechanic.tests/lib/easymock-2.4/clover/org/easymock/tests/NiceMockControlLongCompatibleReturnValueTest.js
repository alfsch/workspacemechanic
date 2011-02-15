var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 239, "sl" : 14, "el" : 56, "name" : "NiceMockControlLongCompatibleReturnValueTest",
    "methods" : [
              {"sl" : 20, "el" : 25, "sc" : 5},  {"sl" : 27, "el" : 31, "sc" : 5},  {"sl" : 33, "el" : 37, "sc" : 5},  {"sl" : 39, "el" : 43, "sc" : 5},  {"sl" : 45, "el" : 49, "sc" : 5},  {"sl" : 51, "el" : 55, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_162" : {
					  "name" : "shortReturningValue",
					  "pass" : true ,
					  "methods" : [{"sl": 33 },],
					  "statements" : [{"sl": 35 },{"sl": 36 },]
					  },
		"test_166" : {
					  "name" : "intReturningValue",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 48 },]
					  },
		"test_167" : {
					  "name" : "longReturningValue",
					  "pass" : true ,
					  "methods" : [{"sl": 51 },],
					  "statements" : [{"sl": 53 },{"sl": 54 },]
					  },
		"test_171" : {
					  "name" : "charReturningValue",
					  "pass" : true ,
					  "methods" : [{"sl": 39 },],
					  "statements" : [{"sl": 41 },{"sl": 42 },]
					  },
		"test_165" : {
					  "name" : "byteReturningValue",
					  "pass" : true ,
					  "methods" : [{"sl": 27 },],
					  "statements" : [{"sl": 29 },{"sl": 30 },]
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
  [  ] ,
  [ 165   ] ,
  [  ] ,
  [ 165   ] ,
  [ 165   ] ,
  [  ] ,
  [  ] ,
  [ 162   ] ,
  [  ] ,
  [ 162   ] ,
  [ 162   ] ,
  [  ] ,
  [  ] ,
  [ 171   ] ,
  [  ] ,
  [ 171   ] ,
  [ 171   ] ,
  [  ] ,
  [  ] ,
  [ 166   ] ,
  [  ] ,
  [ 166   ] ,
  [ 166   ] ,
  [  ] ,
  [  ] ,
  [ 167   ] ,
  [  ] ,
  [ 167   ] ,
  [ 167   ] ,
  [  ] ,
  [  ] 
];
