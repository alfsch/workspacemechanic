var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 3650, "sl" : 15, "el" : 86, "name" : "CallbackAndArgumentsTest",
    "methods" : [
              {"sl" : 19, "el" : 22, "sc" : 5},  {"sl" : 24, "el" : 45, "sc" : 5},  {"sl" : 31, "el" : 34, "sc" : 13},  {"sl" : 47, "el" : 50, "sc" : 5},  {"sl" : 52, "el" : 85, "sc" : 5},  {"sl" : 60, "el" : 63, "sc" : 13},  {"sl" : 68, "el" : 72, "sc" : 13}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_324" : {
					  "name" : "currentArgumentsFailsOutsideCallbacks",
					  "pass" : true ,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },]
					  },
		"test_322" : {
					  "name" : "callbackGetsArgumentsEvenIfAMockCallsAnother",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },{"sl": 60 },{"sl": 68 },],
					  "statements" : [{"sl": 55 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 62 },{"sl": 66 },{"sl": 67 },{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 75 },{"sl": 76 },{"sl": 78 },{"sl": 79 },{"sl": 81 },{"sl": 82 },{"sl": 84 },]
					  },
		"test_330" : {
					  "name" : "callbackGetsArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 24 },{"sl": 31 },],
					  "statements" : [{"sl": 27 },{"sl": 29 },{"sl": 30 },{"sl": 32 },{"sl": 33 },{"sl": 37 },{"sl": 39 },{"sl": 40 },{"sl": 42 },{"sl": 44 },]
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
  [ 330   ] ,
  [  ] ,
  [  ] ,
  [ 330   ] ,
  [  ] ,
  [ 330   ] ,
  [ 330   ] ,
  [ 330   ] ,
  [ 330   ] ,
  [ 330   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 330   ] ,
  [  ] ,
  [ 330   ] ,
  [ 330   ] ,
  [  ] ,
  [ 330   ] ,
  [  ] ,
  [ 330   ] ,
  [  ] ,
  [  ] ,
  [ 324   ] ,
  [  ] ,
  [ 324   ] ,
  [  ] ,
  [  ] ,
  [ 322   ] ,
  [  ] ,
  [  ] ,
  [ 322   ] ,
  [  ] ,
  [ 322   ] ,
  [ 322   ] ,
  [ 322   ] ,
  [ 322   ] ,
  [  ] ,
  [ 322   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 322   ] ,
  [ 322   ] ,
  [ 322   ] ,
  [ 322   ] ,
  [ 322   ] ,
  [ 322   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 322   ] ,
  [ 322   ] ,
  [  ] ,
  [ 322   ] ,
  [ 322   ] ,
  [  ] ,
  [ 322   ] ,
  [ 322   ] ,
  [  ] ,
  [ 322   ] ,
  [  ] ,
  [  ] 
];
