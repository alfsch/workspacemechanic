var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4571, "sl" : 14, "el" : 74, "name" : "RecordStateInvalidRangeTest",
    "methods" : [
              {"sl" : 20, "el" : 24, "sc" : 5},  {"sl" : 26, "el" : 38, "sc" : 5},  {"sl" : 40, "el" : 50, "sc" : 5},  {"sl" : 52, "el" : 62, "sc" : 5},  {"sl" : 64, "el" : 73, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_180" : {
					  "name" : "setIllegalMinimumCount",
					  "pass" : true ,
					  "methods" : [{"sl": 40 },],
					  "statements" : [{"sl": 42 },{"sl": 43 },{"sl": 44 },{"sl": 45 },{"sl": 48 },]
					  },
		"test_138" : {
					  "name" : "setOpenCallCountTwice",
					  "pass" : true ,
					  "methods" : [{"sl": 26 },],
					  "statements" : [{"sl": 28 },{"sl": 29 },{"sl": 30 },{"sl": 31 },{"sl": 34 },]
					  },
		"test_175" : {
					  "name" : "setIllegalMaximumCount",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },],
					  "statements" : [{"sl": 54 },{"sl": 55 },{"sl": 56 },{"sl": 57 },{"sl": 60 },]
					  },
		"test_170" : {
					  "name" : "setMinimumBiggerThanMaximum",
					  "pass" : true ,
					  "methods" : [{"sl": 64 },],
					  "statements" : [{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 71 },]
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
  [ 138   ] ,
  [  ] ,
  [ 138   ] ,
  [ 138   ] ,
  [ 138   ] ,
  [ 138   ] ,
  [  ] ,
  [  ] ,
  [ 138   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 180   ] ,
  [  ] ,
  [ 180   ] ,
  [ 180   ] ,
  [ 180   ] ,
  [ 180   ] ,
  [  ] ,
  [  ] ,
  [ 180   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 175   ] ,
  [  ] ,
  [ 175   ] ,
  [ 175   ] ,
  [ 175   ] ,
  [ 175   ] ,
  [  ] ,
  [  ] ,
  [ 175   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 170   ] ,
  [  ] ,
  [ 170   ] ,
  [ 170   ] ,
  [ 170   ] ,
  [  ] ,
  [  ] ,
  [ 170   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
