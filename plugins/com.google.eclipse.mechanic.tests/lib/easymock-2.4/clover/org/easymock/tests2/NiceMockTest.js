var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2261, "sl" : 14, "el" : 47, "name" : "NiceMockTest",
    "methods" : [
              {"sl" : 18, "el" : 22, "sc" : 5},  {"sl" : 24, "el" : 28, "sc" : 5},  {"sl" : 30, "el" : 34, "sc" : 5},  {"sl" : 36, "el" : 40, "sc" : 5},  {"sl" : 42, "el" : 46, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_45" : {
					  "name" : "defaultReturnValueFloat",
					  "pass" : true ,
					  "methods" : [{"sl": 30 },],
					  "statements" : [{"sl": 32 },{"sl": 33 },]
					  },
		"test_42" : {
					  "name" : "defaultReturnValueBoolean",
					  "pass" : true ,
					  "methods" : [{"sl": 24 },],
					  "statements" : [{"sl": 26 },{"sl": 27 },]
					  },
		"test_39" : {
					  "name" : "defaultReturnValueDouble",
					  "pass" : true ,
					  "methods" : [{"sl": 36 },],
					  "statements" : [{"sl": 38 },{"sl": 39 },]
					  },
		"test_74" : {
					  "name" : "defaultReturnValueObject",
					  "pass" : true ,
					  "methods" : [{"sl": 42 },],
					  "statements" : [{"sl": 44 },{"sl": 45 },]
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
  [ 42   ] ,
  [  ] ,
  [ 42   ] ,
  [ 42   ] ,
  [  ] ,
  [  ] ,
  [ 45   ] ,
  [  ] ,
  [ 45   ] ,
  [ 45   ] ,
  [  ] ,
  [  ] ,
  [ 39   ] ,
  [  ] ,
  [ 39   ] ,
  [ 39   ] ,
  [  ] ,
  [  ] ,
  [ 74   ] ,
  [  ] ,
  [ 74   ] ,
  [ 74   ] ,
  [  ] ,
  [  ] 
];
