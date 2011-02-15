var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 493, "sl" : 12, "el" : 22, "name" : "UsageMatchersTest",
    "methods" : [
              {"sl" : 13, "el" : 20, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_263" : {
					  "name" : "additionalMatchersFailAtReplay",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },],
					  "statements" : [{"sl": 16 },{"sl": 17 },{"sl": 19 },]
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
  [ 263   ] ,
  [  ] ,
  [  ] ,
  [ 263   ] ,
  [ 263   ] ,
  [  ] ,
  [ 263   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
