var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 91, "sl" : 14, "el" : 49, "name" : "NiceMockControlTest",
    "methods" : [
              {"sl" : 19, "el" : 24, "sc" : 5},  {"sl" : 26, "el" : 30, "sc" : 5},  {"sl" : 32, "el" : 36, "sc" : 5},  {"sl" : 38, "el" : 42, "sc" : 5},  {"sl" : 44, "el" : 48, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_77" : {
					  "name" : "defaultReturnValueDouble",
					  "pass" : true ,
					  "methods" : [{"sl": 38 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },]
					  },
		"test_81" : {
					  "name" : "defaultReturnValueFloat",
					  "pass" : true ,
					  "methods" : [{"sl": 32 },],
					  "statements" : [{"sl": 34 },{"sl": 35 },]
					  },
		"test_31" : {
					  "name" : "defaultReturnValueBoolean",
					  "pass" : true ,
					  "methods" : [{"sl": 26 },],
					  "statements" : [{"sl": 28 },{"sl": 29 },]
					  },
		"test_71" : {
					  "name" : "defaultReturnValueObject",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },],
					  "statements" : [{"sl": 46 },{"sl": 47 },]
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
  [ 31   ] ,
  [  ] ,
  [ 31   ] ,
  [ 31   ] ,
  [  ] ,
  [  ] ,
  [ 81   ] ,
  [  ] ,
  [ 81   ] ,
  [ 81   ] ,
  [  ] ,
  [  ] ,
  [ 77   ] ,
  [  ] ,
  [ 77   ] ,
  [ 77   ] ,
  [  ] ,
  [  ] ,
  [ 71   ] ,
  [  ] ,
  [ 71   ] ,
  [ 71   ] ,
  [  ] ,
  [  ] 
];
