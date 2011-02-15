var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 1632, "sl" : 7, "el" : 24, "name" : "LessOrEqual",
    "methods" : [
              {"sl" : 11, "el" : 13, "sc" : 5},  {"sl" : 15, "el" : 18, "sc" : 5},  {"sl" : 20, "el" : 23, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_6" : {
					  "name" : "greaterThan",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 22 },]
					  },
		"test_8" : {
					  "name" : "lessOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 15 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 17 },{"sl": 22 },]
					  },
		"test_183" : {
					  "name" : "testLessOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 15 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 17 },{"sl": 22 },]
					  },
		"test_410" : {
					  "name" : "lessOrEqualOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 22 },]
					  },
		"test_401" : {
					  "name" : "constraints",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 15 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 17 },{"sl": 22 },]
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
  [ 183 , 401 , 6 , 410 , 8   ] ,
  [ 183 , 401 , 6 , 410 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 183 , 401 , 8   ] ,
  [  ] ,
  [ 183 , 401 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 183 , 401 , 6 , 410 , 8   ] ,
  [  ] ,
  [ 183 , 401 , 6 , 410 , 8   ] ,
  [  ] ,
  [  ] 
];
