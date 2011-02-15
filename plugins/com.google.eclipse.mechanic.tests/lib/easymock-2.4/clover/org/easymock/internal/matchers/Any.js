var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4249, "sl" : 11, "el" : 28, "name" : "Any",
    "methods" : [
              {"sl" : 17, "el" : 19, "sc" : 5},  {"sl" : 21, "el" : 23, "sc" : 5},  {"sl" : 25, "el" : 27, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_411" : {
					  "name" : "anyToString",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 25 },],
					  "statements" : [{"sl": 26 },]
					  },
		"test_56" : {
					  "name" : "any",
					  "pass" : true ,
					  "methods" : [{"sl": 21 },],
					  "statements" : [{"sl": 22 },]
					  },
		"test_381" : {
					  "name" : "stubBehavior",
					  "pass" : true ,
					  "methods" : [{"sl": 21 },],
					  "statements" : [{"sl": 22 },]
					  },
		"test_19" : {
					  "name" : "testOr",
					  "pass" : true ,
					  "methods" : [{"sl": 21 },],
					  "statements" : [{"sl": 22 },]
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
  [ 411   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 381 , 19 , 56   ] ,
  [ 381 , 19 , 56   ] ,
  [  ] ,
  [  ] ,
  [ 411   ] ,
  [ 411   ] ,
  [  ] ,
  [  ] 
];
