var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4260, "sl" : 13, "el" : 42, "name" : "Compare",
    "methods" : [
              {"sl" : 23, "el" : 27, "sc" : 5},  {"sl" : 29, "el" : 32, "sc" : 5},  {"sl" : 34, "el" : 40, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_436" : {
					  "name" : "compareWithComparator",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 29 },{"sl": 34 },],
					  "statements" : [{"sl": 24 },{"sl": 25 },{"sl": 26 },{"sl": 30 },{"sl": 36 },{"sl": 37 },{"sl": 39 },]
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
  [ 436   ] ,
  [ 436   ] ,
  [ 436   ] ,
  [ 436   ] ,
  [  ] ,
  [  ] ,
  [ 436   ] ,
  [ 436   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 436   ] ,
  [  ] ,
  [ 436   ] ,
  [ 436   ] ,
  [  ] ,
  [ 436   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
