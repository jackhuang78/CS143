counter = 1
lines = []
file = File.new(ARGV[0], "r")
while (line = file.gets)
	if line[line.length-2] != "_"
    	lines.push(line.delete("\n"))
    end
    counter = counter + 1
end
puts "Total lines read: " + lines.length.to_s
file.close
gen_name = ARGV[0].split(".")[0]+"_gen.cl"
File.open(gen_name, 'w') { |file| 
	for i in 0..1000
		for i in 0..rand(10..20)
			file.write(lines[rand(0..lines.length-1)]) 
			if i.even? 
				file.write(" ") 
			end
		end
		file.write("\n")
	end
}
puts gen_name + " generated! "
#(a..b).to_a.sample