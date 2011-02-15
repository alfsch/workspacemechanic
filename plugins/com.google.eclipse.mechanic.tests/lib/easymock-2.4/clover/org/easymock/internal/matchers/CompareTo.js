var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 1638, "sl" : 11, "el" : 38, "name" : "CompareTo",
    "methods" : [
              {"sl" : 17, "el" : 19, "sc" : 5},  {"sl" : 21, "el" : 29, "sc" : 5},  {"sl" : 31, "el" : 33, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "greaterOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_89" : {
					  "name" : "testNotComparable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 25 },]
					  },
		"test_6" : {
					  "name" : "greaterThan",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_8" : {
					  "name" : "lessOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_405" : {
					  "name" : "lessThanOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_446" : {
					  "name" : "greaterThanOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_10" : {
					  "name" : "lessThan",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_184" : {
					  "name" : "testGreateThan",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_455" : {
					  "name" : "greaterOrEqualOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_179" : {
					  "name" : "testLessThan",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_176" : {
					  "name" : "testGreateOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_174" : {
					  "name" : "testCompareEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_183" : {
					  "name" : "testLessOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_263" : {
					  "name" : "additionalMatchersFailAtReplay",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },],
					  "statements" : [{"sl": 18 },]
					  },
		"test_410" : {
					  "name" : "lessOrEqualOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_401" : {
					  "name" : "constraints",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },{"sl": 31 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },{"sl": 32 },]
					  },
		"test_444" : {
					  "name" : "cmpTo",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 24 },{"sl": 28 },]
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
  [ 89 , 183 , 444 , 401 , 6 , 184 , 3 , 410 , 446 , 455 , 263 , 405 , 10 , 176 , 174 , 179 , 8   ] ,
  [ 89 , 183 , 444 , 401 , 6 , 184 , 3 , 410 , 446 , 455 , 263 , 405 , 10 , 176 , 174 , 179 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 89 , 183 , 444 , 401 , 6 , 184 , 3 , 410 , 446 , 455 , 405 , 10 , 176 , 174 , 179 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 89 , 183 , 444 , 401 , 6 , 184 , 3 , 410 , 446 , 455 , 405 , 10 , 176 , 174 , 179 , 8   ] ,
  [ 89   ] ,
  [  ] ,
  [  ] ,
  [ 183 , 444 , 401 , 6 , 184 , 3 , 410 , 446 , 455 , 405 , 10 , 176 , 174 , 179 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 183 , 401 , 6 , 184 , 3 , 10 , 176 , 174 , 179 , 8   ] ,
  [ 183 , 401 , 6 , 184 , 3 , 10 , 176 , 174 , 179 , 8   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
