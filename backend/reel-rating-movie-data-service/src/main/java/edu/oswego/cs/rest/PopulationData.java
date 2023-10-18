package edu.oswego.cs.rest;

public class PopulationData {
    public void populateDataBase(){

        var db = new DatabaseController();
        
        
        db.createMovie("The Sound of Music","Robert Wise", "1965", "2h. 52min.", "Georg Hurdalek, Howard Lindsay, Russel Crouse", "A nun ends up getting kicked out of the monastery after falling in love with a man and he has like 5 kids. Tragic, but he’s rich so it’s not so bad. He’s divorced. It’s a musical.");

        var movie1 = db.getMovieWithTitle("The Sound of Music");
        db.createTag("Romance", movie1.get().getId(), "David", "public");
        db.createTag("Drama", movie1.get().getId(), "David", "public");
        db.createTag("Family", movie1.get().getId(), "David", "public");
        db.createTag("Biography", movie1.get().getId(), "David", "public");

        db.createRating("Blasphemy Against Vows", "5", "5", "David", movie1.get().getId(), "public");
        db.createRating("Is it worth it?", "10", "10", "David", movie1.get().getId(), "public");
        db.createRating("A cute film", "3", "3", "David", movie1.get().getId(), "public");
        db.createRating("Best Musical", "10", "10", "David", movie1.get().getId(), "public");

        db.createMovie("17 Again", "Burr Steers", "2009", "1h. 42min.", "Jason Filardi",
                "An ungrateful middle-aged man gets the chance to be 17 again because he had a fixation on his high-school glory days and gets a chance to be “17 again” by a magical janitor. His best friend was cool. His wife rightfully wants to divorce him after putting up with his ridiculousness for 20 years. But then he learns to appreciate what he has or something and she decides not to divorce him (unfortunately).");

        var movie2 = db.getMovieWithTitle("17 Again");
        db.createTag("Comedy", movie2.get().getId(), "David", "public");
        db.createTag("Drama", movie2.get().getId(), "David", "public");
        db.createTag("Fantasy", movie2.get().getId(), "David", "public");

        db.createRating("They should get divorced", "3", "3", "David", movie2.get().getId(), "public");
        db.createRating("The best friend was the best character", "7", "7", "David", movie2.get().getId(), "public");
        db.createRating("How cute was Zac Efron", "10", "10", "David", movie2.get().getId(), "public");

        db.createMovie("How to Train Your Dragon", "Dean DeBlois, Chris Sanders", "2010", "1h. 38min.", "William Davies, Dean DeBlois, Chris Sanders",
                "A young viking befriends a cat-like dragon. He also gets a date.");

        var movie3 = db.getMovieWithTitle("How to Train Your Dragon");
        db.createTag("Animation", movie3.get().getId(), "David", "public");
        db.createTag("Action", movie3.get().getId(), "David", "public");
        db.createTag("Adventure", movie3.get().getId(), "David", "public");

        db.createRating("Good animated movies", "6", "6", "David", movie3.get().getId(), "public");
        db.createRating("Cute pet", "7", "7", "David", movie3.get().getId(), "public");
        db.createRating("Would watch again", "5", "5", "David", movie3.get().getId(), "public");

        db.createMovie("Ratatouille", "Brad Bird, Jan Pinkava", "2007", "1h. 51min.", "Brad Bird, Jan Pinkava, Jim Capobianco",
                "A rat can cook and cooks for the son of a famous chef.");

        var movie4 = db.getMovieWithTitle("Ratatouille");
        db.createTag("Animation", movie4.get().getId(), "David", "public");
        db.createTag("Adventure", movie4.get().getId(), "David", "public");
        db.createTag("Comedy", movie4.get().getId(), "David", "public");

        db.createRating("Strong rats", "6", "6", "David", movie4.get().getId(), "public");
        db.createRating("rats everywhere", "7", "7", "David", movie4.get().getId(), "public");
        db.createRating("Too many rats", "5", "5", "David", movie4.get().getId(), "public");

        db.createMovie("Lilo and Stitch", "Dean DeBlois, Chris Sanders", "2002", "1h. 25min.", "Chris Sanders, Dean DeBlois",
                "A girl named Lilo ends up adapting an alien by accident and things go down.");

        var movie5 = db.getMovieWithTitle("Lilo and Stitch");
        db.createTag("Animation", movie5.get().getId(), "David", "public");
        db.createTag("Adventure", movie5.get().getId(), "David", "public");
        db.createTag("Comedy", movie5.get().getId(), "David", "public");
        db.createTag("Family", movie5.get().getId(), "David", "public");

        db.createRating("Best Movies of all time", "10", "10", "David", movie5.get().getId(), "public");
        db.createRating("Best family movies", "6", "6", "David", movie5.get().getId(), "public");
        db.createRating("Paul Approved", "10", "10", "David", movie5.get().getId(), "public");

        db.createMovie("Atlantis: The Lost Empire", "Gary Trousdale, Kirk Wise", "2001", "1h. 35min.", "Tab Murphy, Kirk Wise, Gary Trousdale",
                "A failure of a researcher who’s super dorky ends up going on an expedition to Atlantis, this is an attractive man. And he gets a hot girlfriend at the end of the movie.");

        var movie6 = db.getMovieWithTitle("Atlantis: The Lost Empire");
        db.createTag("Animation", movie6.get().getId(), "David", "public");
        db.createTag("Action", movie6.get().getId(), "David", "public");
        db.createTag("Adventure", movie6.get().getId(), "David", "public");

        db.createRating("Animated movies with somber deaths", "10", "10", "David", movie6.get().getId(), "public");
        db.createRating("Paul Approved", "10", "10", "David", movie6.get().getId(), "public");

        db.createMovie("West Side Story", "Jerome Robbins, Robert Wise", "1961", "2h. 33min.", "Ernest Lehman, Arthur Laurents, Jerome Robbins",
                "Lots of good-looking people fight like Romeo and Juliet in a modern gang thing. It’s a musical.");

        var movie7 = db.getMovieWithTitle("West Side Story");
        db.createTag("Crime", movie7.get().getId(), "David", "public");
        db.createTag("Drama", movie7.get().getId(), "David", "public");
        db.createTag("Musical", movie7.get().getId(), "David", "public");

        db.createRating("Best musical", "10", "10", "David", movie7.get().getId(), "public");
        db.createRating("Feel good movies", "10", "10", "David", movie7.get().getId(), "public");

        db.createMovie("Bring It On", "Peyton Reed", "2000", "1h. 38min.", "Jessica Bendinger",
                "A chick who’s on a cheerleading team that steals routines from another way cooler school ends up being captain and changes her team’s ways. They end up losing to the way cooler team.");

        var movie8 = db.getMovieWithTitle("Bring It On");
        db.createTag("Comedy", movie8.get().getId(), "David", "public");
        db.createTag("Romance", movie8.get().getId(), "David", "public");
        db.createTag("Sport", movie8.get().getId(), "David", "public");

        db.createRating("Best Cheerleading movie", "6", "6", "David", movie8.get().getId(), "public");
        db.createRating("Cutest Outfits", "3", "3", "David", movie8.get().getId(), "public");

        db.createMovie("Enchanted", "Kevin Lima", "2007", "1h. 47min.", "Bill Kelly",
                "An animated fairy-tale chick ends up falling into a well and ends up getting with a man who’s engaged. She was also engaged and having her wedding day before she fell into the well. They end up having an emotional affair and get together at the end of the movie. It’s so cute and their partners get together and live way better lives than them.");

        var movie9 = db.getMovieWithTitle("Enchanted");
        db.createTag("Animation", movie9.get().getId(), "David", "public");
        db.createTag("Adventure", movie9.get().getId(), "David", "public");
        db.createTag("Comedy", movie9.get().getId(), "David", "public");

        db.createRating("Best Movie of ALL TIME", "10", "10", "David", movie9.get().getId(), "public");
        db.createRating("Best side character of all time", "3", "3", "David", movie9.get().getId(), "public");
        db.createRating("Best storyline of all time", "10", "10", "David", movie9.get().getId(), "public");
        db.createRating("Most satisfying ending of all time", "4", "4", "David", movie9.get().getId(), "public");

        db.createMovie("The Road to El Dorado", "Bibo Bergeron, Don Paul, Jeffrey Katzenberg", "2000", "1h. 29min.", "Ted Elliot, Terry Rossio, Karey Kirkpatrick",
                "Two brother-like friends end up stranded on an island and deceive a bunch of natives into thinking they’re gods. They end up saving the island and leaving it though.");

        var movie10 = db.getMovieWithTitle("The Road to El Dorado");
        db.createTag("Animation", movie10.get().getId(), "David", "public");
        db.createTag("Adventure", movie10.get().getId(), "David", "public");
        db.createTag("Comedy", movie10.get().getId(), "David", "public");

        db.createRating("BEST movie of all time", "10", "10", "David", movie10.get().getId(), "public");
        db.createRating("Best bros United", "8", "8", "David", movie10.get().getId(), "public");
        db.createRating("Summer-time movies", "9", "9", "David", movie10.get().getId(), "public");
        db.createRating("Paul Approved", "10", "10", "David", movie10.get().getId(), "public");

        db.createMovie("Barbie", "Greta Gerwig", "2023", "1h. 45min.", "Greta Gerwig, Noah Baumbach",
                "Barbie becomes imperfect and wants to fix herself so she goes to the real world and then wants to stay imperfect. Ken is also a menace.");

        var movie11 = db.getMovieWithTitle("Barbie");
        db.createTag("Adventure", movie11.get().getId(), "David", "public");
        db.createTag("Comedy", movie11.get().getId(), "David", "public");
        db.createTag("Fantasy", movie11.get().getId(), "David", "public");

        db.createRating("Camp movies", "10", "10", "David", movie11.get().getId(), "public");
        db.createRating("Existential Crises", "5", "5", "David", movie11.get().getId(), "public");
        db.createRating("2023 Movies", "8", "8", "David", movie11.get().getId(), "public");

        db.createMovie("Barbie as The Princess and the Pauper", "William Lau", "2004", "1h. 25min.", "Cliff Ruby, Elana Lesser, Mark Twain",
                "An adaption of the prince and the pauper but with Barbie.");

        var movie12 = db.getMovieWithTitle("Barbie as The Princess and the Pauper");
        db.createTag("Animation", movie12.get().getId(), "David", "public");
        db.createTag("Comedy", movie12.get().getId(), "David", "public");
        db.createTag("Family", movie12.get().getId(), "David", "public");

        db.createRating("Best Barbie movie of all time", "10", "10", "David", movie12.get().getId(), "public");
        db.createRating("Best animated movie of all time", "5", "5", "David", movie12.get().getId(), "public");
        db.createRating("Cutest Couples in Movies", "2", "2", "David", movie12.get().getId(), "public");

        db.createMovie("Cinderella", "Robert Iscove", "1997", "1h. 28min.", "Oscar Hammerstein 2, Robert L. Freedman, Charles Perrault",
                "A chick who can’t throw hands ends up meeting a prince and they live happily ever after.");

        var movie13 = db.getMovieWithTitle("Cinderella");
        db.createTag("Family", movie13.get().getId(), "David", "public");
        db.createTag("Fantasy", movie13.get().getId(), "David", "public");
        db.createTag("Musical", movie13.get().getId(), "David", "public");

        db.createRating("Live-Action Princess Movie", "4", "4", "David", movie13.get().getId(), "public");
        db.createRating("Confusing movies", "10", "10", "David", movie13.get().getId(), "public");

        db.createMovie("The Lion King", "Roger Allers, Rob Minkoff", "1994", "1h. 28min.", "Irene Mecchi, Jonathan Roberts, Linda Woolverton",
                "Young lion cub murders his father cause he can’t follow instructions. He then fights his uncle to take his father’s place as leader of the lion pack.");

        var movie14 = db.getMovieWithTitle("The Lion King");
        db.createTag("Animation", movie14.get().getId(), "David", "public");
        db.createTag("Adventure", movie14.get().getId(), "David", "public");
        db.createTag("Drama", movie14.get().getId(), "David", "public");

        db.createRating("Best animated movie of all time", "7", "7", "David", movie14.get().getId(), "public");

        db.createMovie("The Princess and the Frog", "Ron Clements, John Musker", "2009", "1h. 37min.", "Ron Clements, John Musker, Greg Erb",
                "A girl who has life figured out gets bothered by a frog and her life becomes awful. But they fall in love and live happily ever after.");

        var movie15 = db.getMovieWithTitle("The Princess and the Frog");
        db.createTag("Animation", movie15.get().getId(), "David", "public");
        db.createTag("Adventure", movie15.get().getId(), "David", "public");
        db.createTag("Comedy", movie15.get().getId(), "David", "public");

        db.createRating("Animated Disney Movie Goodness", "2", "2", "David", movie15.get().getId(), "public");
        db.createRating("Princess PrincessNess", "8", "8", "David", movie15.get().getId(), "public");
        db.createRating("Aesthetic", "6", "6", "David", movie15.get().getId(), "public");

        db.createMovie("Tangled", "Nathan Greno, Byron Howard", "2010", "1h. 40min.", "Dan Fogelman, Jacob Grimm, Wilhelm Grimm",
                "A girl leaves the tower that her mom doesn’t want her to and falls in love. She was also imprisoned in said tower for years. And stolen from her parents.");

        var movie16 = db.getMovieWithTitle("Tangled");
        db.createTag("Animation", movie16.get().getId(), "David", "public");
        db.createTag("Adventure", movie16.get().getId(), "David", "public");
        db.createTag("Comedy", movie16.get().getId(), "David", "public");

        db.createRating("Disney Princess Movie", "10", "10", "David", movie16.get().getId(), "public");
        db.createRating("Quirky Disney Character rating", "7", "7", "David", movie16.get().getId(), "public");
        db.createRating("Best animated movie", "8", "8", "David", movie16.get().getId(),"public");

        db.createMovie("Mulan", "Tony Bancroft, Barry Cook", "1998", "1h. 27 min.", "Robert D. San Souci, Rita Hsiao, Chris Sanders",
                "A girl goes to war in place of her father and saves China. She also has a pet dragon and takes home a hot general.");

        var movie17 = db.getMovieWithTitle("Mulan");
        db.createTag("Animation", movie17.get().getId(), "David", "public");
        db.createTag("Adventure", movie17.get().getId(), "David", "public");
        db.createTag("Comedy", movie17.get().getId(), "David", "public");

        db.createRating("Disney Princess Movie", "10", "10", "David", movie17.get().getId(), "public");
        db.createRating("Princess PrincessNess", "8", "8", "David", movie17.get().getId(), "public");
        db.createRating("Best animated movie of all time", "10", "10", "David", movie17.get().getId(), "public");
        db.createRating("Best Movies of all time", "10", "10", "David", movie17.get().getId(), "public");

        db.createMovie("Shang-Chi and the Legend of the 10 Rings", "Destin Daniel Cretton", "2021", "2h. 12 min.", "Dave Callaham, Destin Daniel Cretton, Andrew Lanham",
                "A boy becomes a superhero because he has to fight his father or something.");

        var movie18 = db.getMovieWithTitle("Shang-Chi and the Legend of the 10 Rings");
        db.createTag("Action", movie18.get().getId(), "David", "public");
        db.createTag("Adventure", movie18.get().getId(), "David", "public");
        db.createTag("Comedy", movie18.get().getId(), "David", "public");

        db.createRating("Superhero origins", "4", "4", "David", movie18.get().getId(), "public");
        db.createRating("Cutest male lead", "9", "9", "David", movie18.get().getId(), "public");
        db.createRating("Best weapons", "7", "7", "David", movie18.get().getId(), "public");

        db.createMovie("Everything Everywhere All at Once", "Daniel Kwan, Daniel Scheinert", "2022", "2h. 19min.", "Daniel Kwan, Daniel Scheinert",
                "A middle-aged Chinese immigrant goes on an adventure exploring other universes.");

        var movie19 = db.getMovieWithTitle("Everything Everywhere All at Once");
        db.createTag("Action", movie19.get().getId(), "David", "public");
        db.createTag("Adventure", movie19.get().getId(), "David", "public");
        db.createTag("Comedy", movie19.get().getId(), "David", "public");

        db.createRating("Costume rating", "7", "7", "David", movie19.get().getId(), "public");
        db.createRating("Weirdest people", "4", "4", "David", movie19.get().getId(), "public");
        db.createRating("Coolest plot", "3", "3", "David", movie19.get().getId(), "public");

        db.createMovie("Spider-Man: Across the Spider-Verse", "Joaquim Dos Santos, Kemp Powers, Justin K. Thompson", "2023", "2h. 20min.", "Phil Lord, Christopher Miller, Dave Callaham",
                "Young Spider Boy has another spider-verse adventure.");

        var movie20 = db.getMovieWithTitle("Spider-Man: Across the Spider-Verse");
        db.createTag("Animation", movie20.get().getId(), "David", "public");
        db.createTag("Action", movie20.get().getId(), "David", "public");
        db.createTag("Adventure", movie20.get().getId(), "David", "public");

        db.createRating("Best animated movie", "10", "10", "David", movie20.get().getId(), "public");
        db.createRating("Movie of the year", "9", "9", "David", movie20.get().getId(), "public");
        db.createRating("Character drawing rating", "3", "3", "David", movie20.get().getId(), "public");
        db.createRating("Animation awesomeness", "4", "4", "David", movie20.get().getId(), "public");

        //DataBase population for actors:

        db.createActor("Julie Andrews", "10/01/1935", movie1.get().getId());
        db.createActor("Christopher Plummer", "12/13/1929", movie1.get().getId());
        db.createActor("Eleanor Parker", "06/26/1922", movie1.get().getId());

        db.createActor("Zac Efron", "10/18/1987", movie2.get().getId());
        db.createActor("Matthew Perry", "08/19/1922", movie2.get().getId());
        db.createActor("Leslie Mann", "03/26/1972", movie2.get().getId());

        db.createActor("Jay Baruchel", "04/09/1982", movie3.get().getId());
        db.createActor("Gerard Butler", "11/13/1969", movie3.get().getId());
        db.createActor("Christopher Mintz-Plasse", "06/20/1989", movie3.get().getId());

        db.createActor("Brad Garrett", "04/14/1960", movie4.get().getId());
        db.createActor("Lou Romano", "04/15/1971", movie4.get().getId());
        db.createActor("Paton Oswalt", "01/27/1969", movie4.get().getId());


        db.createActor("Michael J. Fox", "06/09/1961", movie6.get().getId());
        db.createActor("Jim Varney", "06/15/1949", movie6.get().getId());
        db.createActor("Corey Burton", "08/03/1955", movie6.get().getId());

        db.createActor("Brandy Norwood", "02/11/1979", movie13.get().getId());
        db.createActor("Bernadette Peters", "02/28/1948",movie13.get().getId());

        db.createActor("Simu Liu", "04/19/1989", movie18.get().getId());
        db.createActor("Awkwafina", "06/02/1988", movie18.get().getId());
    }
}
