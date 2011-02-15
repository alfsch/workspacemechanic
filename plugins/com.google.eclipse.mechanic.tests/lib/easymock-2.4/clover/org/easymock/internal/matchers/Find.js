var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 107, "sl" : 12, "el" : 30, "name" : "Find",
    "methods" : [
              {"sl" : 18, "el" : 20, "sc" : 5},  {"sl" : 22, "el" : 25, "sc" : 5},  {"sl" : 27, "el" : 29, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_434" : {
					  "name" : "findToString",
					  "pass" : true ,
					  "methods" : [{"sl": 18 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 28 },]
					  },
		"test_143" : {
					  "name" : "testFind",
					  "pass" : true ,
					  "methods" : [{"sl": 18 },{"sl": 22 },],
					  "statements" : [{"sl": 19 },{"sl": 23 },]
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
  [ 434 , 143   ] ,
  [ 434 , 143   ] ,
  [  ] ,
  [  ] ,
  [ 143   ] ,
  [ 143   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 434   ] ,
  [ 434   ] ,
  [  ] ,
  [  ] 
];
