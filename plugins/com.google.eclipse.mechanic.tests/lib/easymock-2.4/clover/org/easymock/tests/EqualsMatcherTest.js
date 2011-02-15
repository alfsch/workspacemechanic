var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2789, "sl" : 14, "el" : 33, "name" : "EqualsMatcherTest",
    "methods" : [
              {"sl" : 17, "el" : 27, "sc" : 5},  {"sl" : 29, "el" : 32, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_281" : {
					  "name" : "differentNumberOfArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 29 },],
					  "statements" : [{"sl": 31 },]
					  },
		"test_286" : {
					  "name" : "equalsMatcher",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 21 },{"sl": 22 },{"sl": 23 },{"sl": 24 },{"sl": 26 },]
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
  [ 286   ] ,
  [  ] ,
  [ 286   ] ,
  [ 286   ] ,
  [ 286   ] ,
  [ 286   ] ,
  [ 286   ] ,
  [ 286   ] ,
  [  ] ,
  [ 286   ] ,
  [  ] ,
  [  ] ,
  [ 281   ] ,
  [  ] ,
  [ 281   ] ,
  [  ] ,
  [  ] 
];
