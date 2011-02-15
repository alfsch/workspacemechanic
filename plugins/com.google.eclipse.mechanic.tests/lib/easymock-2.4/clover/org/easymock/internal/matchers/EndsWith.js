var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 1717, "sl" : 11, "el" : 28, "name" : "EndsWith",
    "methods" : [
              {"sl" : 17, "el" : 19, "sc" : 5},  {"sl" : 21, "el" : 23, "sc" : 5},  {"sl" : 25, "el" : 27, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_438" : {
					  "name" : "endsWithToString",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 25 },],
					  "statements" : [{"sl": 18 },{"sl": 26 },]
					  },
		"test_169" : {
					  "name" : "testEndsWith",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 22 },]
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
  [ 438 , 169   ] ,
  [ 438 , 169   ] ,
  [  ] ,
  [  ] ,
  [ 169   ] ,
  [ 169   ] ,
  [  ] ,
  [  ] ,
  [ 438   ] ,
  [ 438   ] ,
  [  ] ,
  [  ] 
];
