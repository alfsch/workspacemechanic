var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4751, "sl" : 10, "el" : 75, "name" : "NameTest",
    "methods" : [
              {"sl" : 11, "el" : 23, "sc" : 5},  {"sl" : 24, "el" : 36, "sc" : 5},  {"sl" : 37, "el" : 49, "sc" : 5},  {"sl" : 50, "el" : 63, "sc" : 5},  {"sl" : 65, "el" : 73, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_404" : {
					  "name" : "nameForMock",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 13 },{"sl": 14 },{"sl": 15 },{"sl": 16 },{"sl": 17 },{"sl": 19 },{"sl": 20 },{"sl": 21 },]
					  },
		"test_408" : {
					  "name" : "nameForStrictMock",
					  "pass" : true ,
					  "methods" : [{"sl": 24 },],
					  "statements" : [{"sl": 26 },{"sl": 27 },{"sl": 28 },{"sl": 29 },{"sl": 30 },{"sl": 32 },{"sl": 33 },{"sl": 34 },]
					  },
		"test_430" : {
					  "name" : "shouldThrowIllegalArgumentExceptionIfNameIsNoValidJavaIdentifier",
					  "pass" : true ,
					  "methods" : [{"sl": 65 },],
					  "statements" : [{"sl": 67 },{"sl": 68 },{"sl": 71 },]
					  },
		"test_417" : {
					  "name" : "nameForMocksControl",
					  "pass" : true ,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 52 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 56 },{"sl": 57 },{"sl": 59 },{"sl": 60 },{"sl": 61 },]
					  },
		"test_419" : {
					  "name" : "nameForNiceMock",
					  "pass" : true ,
					  "methods" : [{"sl": 37 },],
					  "statements" : [{"sl": 39 },{"sl": 40 },{"sl": 41 },{"sl": 42 },{"sl": 43 },{"sl": 45 },{"sl": 46 },{"sl": 47 },]
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
  [ 404   ] ,
  [  ] ,
  [ 404   ] ,
  [ 404   ] ,
  [ 404   ] ,
  [ 404   ] ,
  [ 404   ] ,
  [  ] ,
  [ 404   ] ,
  [ 404   ] ,
  [ 404   ] ,
  [  ] ,
  [  ] ,
  [ 408   ] ,
  [  ] ,
  [ 408   ] ,
  [ 408   ] ,
  [ 408   ] ,
  [ 408   ] ,
  [ 408   ] ,
  [  ] ,
  [ 408   ] ,
  [ 408   ] ,
  [ 408   ] ,
  [  ] ,
  [  ] ,
  [ 419   ] ,
  [  ] ,
  [ 419   ] ,
  [ 419   ] ,
  [ 419   ] ,
  [ 419   ] ,
  [ 419   ] ,
  [  ] ,
  [ 419   ] ,
  [ 419   ] ,
  [ 419   ] ,
  [  ] ,
  [  ] ,
  [ 417   ] ,
  [  ] ,
  [ 417   ] ,
  [ 417   ] ,
  [ 417   ] ,
  [ 417   ] ,
  [ 417   ] ,
  [ 417   ] ,
  [  ] ,
  [ 417   ] ,
  [ 417   ] ,
  [ 417   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 430   ] ,
  [  ] ,
  [ 430   ] ,
  [ 430   ] ,
  [  ] ,
  [  ] ,
  [ 430   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
