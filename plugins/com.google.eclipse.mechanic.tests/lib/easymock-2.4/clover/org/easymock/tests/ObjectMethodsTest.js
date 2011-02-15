var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2765, "sl" : 17, "el" : 81, "name" : "ObjectMethodsTest",
    "methods" : [
             {"sl" : 25, "el" : 29, "sc" : 5},  {"sl" : 31, "el" : 35, "sc" : 5},  {"sl" : 37, "el" : 42, "sc" : 5},  {"sl" : 44, "el" : 50, "sc" : 5},  {"sl" : 52, "el" : 56, "sc" : 5},  {"sl" : 58, "el" : 63, "sc" : 5},  {"sl" : 73, "el" : 79, "sc" : 5}  ]}
    ,
    {"id" : 2765, "sl" : 22, "el" : 23, "name" : "ObjectMethodsTest.EmptyInterface",
    "methods" : [
             ]}
    ,
    {"id" : 2785, "sl" : 65, "el" : 66, "name" : "ObjectMethodsTest.MockedClass",
    "methods" : [
             ]}
    ,
    {"id" : 2785, "sl" : 68, "el" : 69, "name" : "ObjectMethodsTest.DummyProxy",
    "methods" : [
              ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_284" : {
					  "name" : "toStringForClasses",
					  "pass" : true ,
					  "methods" : [{"sl": 73 },],
					  "statements" : [{"sl": 75 },{"sl": 76 },{"sl": 77 },]
					  },
		"test_298" : {
					  "name" : "testHashCode",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },],
					  "statements" : [{"sl": 46 },{"sl": 47 },{"sl": 48 },{"sl": 49 },]
					  },
		"test_301" : {
					  "name" : "equalsAfterActivation",
					  "pass" : true ,
					  "methods" : [{"sl": 37 },],
					  "statements" : [{"sl": 39 },{"sl": 40 },{"sl": 41 },]
					  },
		"test_300" : {
					  "name" : "equalsBeforeActivation",
					  "pass" : true ,
					  "methods" : [{"sl": 31 },],
					  "statements" : [{"sl": 33 },{"sl": 34 },]
					  },
		"test_302" : {
					  "name" : "toStringBeforeActivation",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },],
					  "statements" : [{"sl": 54 },]
					  },
		"test_304" : {
					  "name" : "toStringAfterActivation",
					  "pass" : true ,
					  "methods" : [{"sl": 58 },],
					  "statements" : [{"sl": 60 },{"sl": 61 },]
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
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 300   ] ,
  [  ] ,
  [ 300   ] ,
  [ 300   ] ,
  [  ] ,
  [  ] ,
  [ 301   ] ,
  [  ] ,
  [ 301   ] ,
  [ 301   ] ,
  [ 301   ] ,
  [  ] ,
  [  ] ,
  [ 298   ] ,
  [  ] ,
  [ 298   ] ,
  [ 298   ] ,
  [ 298   ] ,
  [ 298   ] ,
  [  ] ,
  [  ] ,
  [ 302   ] ,
  [  ] ,
  [ 302   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 304   ] ,
  [  ] ,
  [ 304   ] ,
  [ 304   ] ,
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
  [ 284   ] ,
  [  ] ,
  [ 284   ] ,
  [ 284   ] ,
  [ 284   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
