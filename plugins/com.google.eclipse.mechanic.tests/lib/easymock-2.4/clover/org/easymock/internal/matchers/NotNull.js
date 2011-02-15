var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2078, "sl" : 11, "el" : 28, "name" : "NotNull",
    "methods" : [
              {"sl" : 17, "el" : 19, "sc" : 5},  {"sl" : 21, "el" : 23, "sc" : 5},  {"sl" : 25, "el" : 27, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_364" : {
					  "name" : "differentConstraintsOnSameCall",
					  "pass" : true ,
					  "methods" : [{"sl": 21 },],
					  "statements" : [{"sl": 22 },]
					  },
		"test_145" : {
					  "name" : "testNotNull",
					  "pass" : true ,
					  "methods" : [{"sl": 21 },],
					  "statements" : [{"sl": 22 },]
					  },
		"test_322" : {
					  "name" : "callbackGetsArgumentsEvenIfAMockCallsAnother",
					  "pass" : true ,
					  "methods" : [{"sl": 21 },{"sl": 25 },],
					  "statements" : [{"sl": 22 },{"sl": 26 },]
					  },
		"test_407" : {
					  "name" : "notNullToString",
					  "pass" : true ,
					  "methods" : [{"sl": 25 },],
					  "statements" : [{"sl": 26 },]
					  },
		"test_330" : {
					  "name" : "callbackGetsArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 25 },],
					  "statements" : [{"sl": 22 },{"sl": 26 },]
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
  [ 330   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 145 , 364 , 330 , 322   ] ,
  [ 145 , 364 , 330 , 322   ] ,
  [  ] ,
  [  ] ,
  [ 330 , 322 , 407   ] ,
  [ 330 , 322 , 407   ] ,
  [  ] ,
  [  ] 
];
