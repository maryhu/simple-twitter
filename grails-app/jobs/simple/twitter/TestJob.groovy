package simple.twitter



class TestJob {
    static triggers = {
      simple name:"testTrigger", repeatInterval: 5000l // execute job once in 5 seconds
    }
	def group = "testGroup"

    def execute() {
        print "job run!"
    }
}
