var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 750, "sl" : 15, "el" : 139, "name" : "ReplayStateInvalidCallsTest",
    "methods" : [
              {"sl" : 21, "el" : 25, "sc" : 5},  {"sl" : 27, "el" : 30, "sc" : 5},  {"sl" : 32, "el" : 35, "sc" : 5},  {"sl" : 37, "el" : 40, "sc" : 5},  {"sl" : 42, "el" : 45, "sc" : 5},  {"sl" : 47, "el" : 50, "sc" : 5},  {"sl" : 52, "el" : 55, "sc" : 5},  {"sl" : 57, "el" : 60, "sc" : 5},  {"sl" : 62, "el" : 65, "sc" : 5},  {"sl" : 67, "el" : 70, "sc" : 5},  {"sl" : 72, "el" : 75, "sc" : 5},  {"sl" : 77, "el" : 80, "sc" : 5},  {"sl" : 82, "el" : 85, "sc" : 5},  {"sl" : 87, "el" : 90, "sc" : 5},  {"sl" : 92, "el" : 95, "sc" : 5},  {"sl" : 97, "el" : 100, "sc" : 5},  {"sl" : 102, "el" : 108, "sc" : 5},  {"sl" : 105, "el" : 106, "sc" : 13},  {"sl" : 110, "el" : 113, "sc" : 5},  {"sl" : 115, "el" : 118, "sc" : 5},  {"sl" : 120, "el" : 123, "sc" : 5},  {"sl" : 125, "el" : 128, "sc" : 5},  {"sl" : 130, "el" : 133, "sc" : 5},  {"sl" : 135, "el" : 138, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_352" : {
					  "name" : "setDefaultMatcher",
					  "pass" : true ,
					  "methods" : [{"sl": 37 },],
					  "statements" : [{"sl": 39 },]
					  },
		"test_359" : {
					  "name" : "andAnswer",
					  "pass" : true ,
					  "methods" : [{"sl": 120 },],
					  "statements" : [{"sl": 122 },]
					  },
		"test_354" : {
					  "name" : "expectAndThrowLongWithMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 27 },],
					  "statements" : [{"sl": 29 },]
					  },
		"test_370" : {
					  "name" : "andStubAnswer",
					  "pass" : true ,
					  "methods" : [{"sl": 92 },],
					  "statements" : [{"sl": 94 },]
					  },
		"test_368" : {
					  "name" : "andStubReturn",
					  "pass" : true ,
					  "methods" : [{"sl": 82 },],
					  "statements" : [{"sl": 84 },]
					  },
		"test_371" : {
					  "name" : "andStubThrow",
					  "pass" : true ,
					  "methods" : [{"sl": 87 },],
					  "statements" : [{"sl": 89 },]
					  },
		"test_348" : {
					  "name" : "setDefaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 57 },],
					  "statements" : [{"sl": 59 },]
					  },
		"test_374" : {
					  "name" : "times",
					  "pass" : true ,
					  "methods" : [{"sl": 97 },],
					  "statements" : [{"sl": 99 },]
					  },
		"test_369" : {
					  "name" : "makeThreadSafe",
					  "pass" : true ,
					  "methods" : [{"sl": 77 },],
					  "statements" : [{"sl": 79 },]
					  },
		"test_347" : {
					  "name" : "setDefaultVoidCallable",
					  "pass" : true ,
					  "methods" : [{"sl": 62 },],
					  "statements" : [{"sl": 64 },]
					  },
		"test_365" : {
					  "name" : "defaultVoidCallable",
					  "pass" : true ,
					  "methods" : [{"sl": 135 },],
					  "statements" : [{"sl": 137 },]
					  },
		"test_367" : {
					  "name" : "replay",
					  "pass" : true ,
					  "methods" : [{"sl": 67 },],
					  "statements" : [{"sl": 69 },]
					  },
		"test_372" : {
					  "name" : "callback",
					  "pass" : true ,
					  "methods" : [{"sl": 102 },],
					  "statements" : [{"sl": 104 },]
					  },
		"test_362" : {
					  "name" : "defaultReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 130 },],
					  "statements" : [{"sl": 132 },]
					  },
		"test_349" : {
					  "name" : "setDefaultReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },],
					  "statements" : [{"sl": 54 },]
					  },
		"test_356" : {
					  "name" : "andReturn",
					  "pass" : true ,
					  "methods" : [{"sl": 110 },],
					  "statements" : [{"sl": 112 },]
					  },
		"test_366" : {
					  "name" : "checkOrder",
					  "pass" : true ,
					  "methods" : [{"sl": 72 },],
					  "statements" : [{"sl": 74 },]
					  },
		"test_351" : {
					  "name" : "asStub",
					  "pass" : true ,
					  "methods" : [{"sl": 42 },],
					  "statements" : [{"sl": 44 },]
					  },
		"test_360" : {
					  "name" : "andThrow",
					  "pass" : true ,
					  "methods" : [{"sl": 115 },],
					  "statements" : [{"sl": 117 },]
					  },
		"test_363" : {
					  "name" : "defaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 125 },],
					  "statements" : [{"sl": 127 },]
					  },
		"test_353" : {
					  "name" : "expectAndReturnObjectWithMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 32 },],
					  "statements" : [{"sl": 34 },]
					  },
		"test_350" : {
					  "name" : "setMatcher",
					  "pass" : true ,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },]
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
  [ 354   ] ,
  [  ] ,
  [ 354   ] ,
  [  ] ,
  [  ] ,
  [ 353   ] ,
  [  ] ,
  [ 353   ] ,
  [  ] ,
  [  ] ,
  [ 352   ] ,
  [  ] ,
  [ 352   ] ,
  [  ] ,
  [  ] ,
  [ 351   ] ,
  [  ] ,
  [ 351   ] ,
  [  ] ,
  [  ] ,
  [ 350   ] ,
  [  ] ,
  [ 350   ] ,
  [  ] ,
  [  ] ,
  [ 349   ] ,
  [  ] ,
  [ 349   ] ,
  [  ] ,
  [  ] ,
  [ 348   ] ,
  [  ] ,
  [ 348   ] ,
  [  ] ,
  [  ] ,
  [ 347   ] ,
  [  ] ,
  [ 347   ] ,
  [  ] ,
  [  ] ,
  [ 367   ] ,
  [  ] ,
  [ 367   ] ,
  [  ] ,
  [  ] ,
  [ 366   ] ,
  [  ] ,
  [ 366   ] ,
  [  ] ,
  [  ] ,
  [ 369   ] ,
  [  ] ,
  [ 369   ] ,
  [  ] ,
  [  ] ,
  [ 368   ] ,
  [  ] ,
  [ 368   ] ,
  [  ] ,
  [  ] ,
  [ 371   ] ,
  [  ] ,
  [ 371   ] ,
  [  ] ,
  [  ] ,
  [ 370   ] ,
  [  ] ,
  [ 370   ] ,
  [  ] ,
  [  ] ,
  [ 374   ] ,
  [  ] ,
  [ 374   ] ,
  [  ] ,
  [  ] ,
  [ 372   ] ,
  [  ] ,
  [ 372   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 356   ] ,
  [  ] ,
  [ 356   ] ,
  [  ] ,
  [  ] ,
  [ 360   ] ,
  [  ] ,
  [ 360   ] ,
  [  ] ,
  [  ] ,
  [ 359   ] ,
  [  ] ,
  [ 359   ] ,
  [  ] ,
  [  ] ,
  [ 363   ] ,
  [  ] ,
  [ 363   ] ,
  [  ] ,
  [  ] ,
  [ 362   ] ,
  [  ] ,
  [ 362   ] ,
  [  ] ,
  [  ] ,
  [ 365   ] ,
  [  ] ,
  [ 365   ] ,
  [  ] ,
  [  ] 
];
