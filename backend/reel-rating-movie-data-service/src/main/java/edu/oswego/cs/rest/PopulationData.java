package edu.oswego.cs.rest;

public class PopulationData {
    public void populateDataBase(){
        
        var db = new DatabaseController();
        
        
        db.createMovie("The Sound of Music","Robert Wise", "1965", "2h. 52min.", "Georg Hurdalek, Howard Lindsay, Russel Crouse", "A nun ends up getting kicked out of the monastery after falling in love with a man and he has like 5 kids. Tragic, but he’s rich so it’s not so bad. He’s divorced. It’s a musical.");

        var movie1 = db.getMovieWithTitle("The Sound of Music");
        db.createTag("Romance", movie1.get().getId());
        db.createTag("Drama", movie1.get().getId());
        db.createTag("Family", movie1.get().getId());
        db.createTag("Biography", movie1.get().getId());

        db.createRating("Blasphemy Against Vows", "5", "5", "David", movie1.get().getId(), "");
        db.createRating("Is it worth it?", "10", "10", "David", movie1.get().getId(), "");
        db.createRating("A cute film", "3", "3", "David", movie1.get().getId(), "");
        db.createRating("Best Musical", "10", "10", "David", movie1.get().getId(), "");

        db.createMovie("17 Again", "Burr Steers", "2009", "1h. 42min.", "Jason Filardi",
                "An ungrateful middle-aged man gets the chance to be 17 again because he had a fixation on his high-school glory days and gets a chance to be “17 again” by a magical janitor. His best friend was cool. His wife rightfully wants to divorce him after putting up with his ridiculousness for 20 years. But then he learns to appreciate what he has or something and she decides not to divorce him (unfortunately).");

        var movie2 = db.getMovieWithTitle("17 Again");
        db.createTag("Comedy", movie2.get().getId());
        db.createTag("Drama", movie2.get().getId());
        db.createTag("Fantasy", movie2.get().getId());

        db.createRating("They should get divorced", "3", "3", "David", movie2.get().getId(), "");
        db.createRating("The best friend was the best character", "7", "7", "David", movie2.get().getId(), "");
        db.createRating("How cute was Zac Efron", "10", "10", "David", movie2.get().getId(), "");

        db.createMovie("How to Train Your Dragon", "Dean DeBlois, Chris Sanders", "2010", "1h. 38min.", "William Davies, Dean DeBlois, Chris Sanders",
                "A young viking befriends a cat-like dragon. He also gets a date.");

        var movie3 = db.getMovieWithTitle("How to Train Your Dragon");
        db.createTag("Animation", movie3.get().getId());
        db.createTag("Action", movie3.get().getId());
        db.createTag("Adventure", movie3.get().getId());

        db.createRating("Good animated movies", "6", "6", "David", movie3.get().getId(), "");
        db.createRating("Cute pet", "7", "7", "David", movie3.get().getId(), "");
        db.createRating("Would watch again", "5", "5", "David", movie3.get().getId(), "");

        db.createMovie("Ratatouille", "Brad Bird, Jan Pinkava", "2007", "1h. 51min.", "Brad Bird, Jan Pinkava, Jim Capobianco",
                "A rat can cook and cooks for the son of a famous chef.");

        var movie4 = db.getMovieWithTitle("Ratatouille");
        db.createTag("Animation", movie4.get().getId());
        db.createTag("Adventure", movie4.get().getId());
        db.createTag("Comedy", movie4.get().getId());

        db.createRating("Strong rats", "6", "6", "David", movie4.get().getId(), "");
        db.createRating("Animal prowess", "7", "7", "David", movie4.get().getId(), "");
        db.createRating("Dirty rat", "5", "5", "David", movie4.get().getId(), "");

        db.createMovie("Lilo and Stitch", "Dean DeBlois, Chris Sanders", "2002", "1h. 25min.", "Chris Sanders, Dean DeBlois",
                "A girl named Lilo ends up adapting an alien by accident and things go down.");

        var movie5 = db.getMovieWithTitle("Lilo and Stitch");
        db.createTag("Animation", movie5.get().getId());
        db.createTag("Adventure", movie5.get().getId());
        db.createTag("Comedy", movie5.get().getId());
        db.createTag("Family", movie5.get().getId());

        db.createRating("Best Movies of all time", "10", "10", "David", movie5.get().getId(), "");
        db.createRating("Best family movies", "6", "6", "David", movie5.get().getId(), "");
        db.createRating("Hottest animated character", "10", "10", "David", movie5.get().getId(), "");

        db.createMovie("Atlantis: The Lost Empire", "Gary Trousdale, Kirk Wise", "2001", "1h. 35min.", "Tab Murphy, Kirk Wise, Gary Trousdale",
                "A failure of a researcher who’s super dorky ends up going on an expedition to Atlantis, this is an attractive man. And he gets a hot girlfriend at the end of the movie.");

        var movie6 = db.getMovieWithTitle("Atlantis: The Lost Empire");
        db.createTag("Animation", movie6.get().getId());
        db.createTag("Action", movie6.get().getId());
        db.createTag("Adventure", movie6.get().getId());

        db.createRating("Animated movies with somber deaths", "10", "10", "David", movie6.get().getId(), "");
        db.createRating("Hottest animated character", "10", "10", "David", movie6.get().getId(), "");

        db.createMovie("West Side Story", "Jerome Robbins, Robert Wise", "1961", "2h. 33min.", "Ernest Lehman, Arthur Laurents, Jerome Robbins",
                "Lots of good-looking people fight like Romeo and Juliet in a modern gang thing. It’s a musical.");

        var movie7 = db.getMovieWithTitle("West Side Story");
        db.createTag("Crime", movie7.get().getId());
        db.createTag("Drama", movie7.get().getId());
        db.createTag("Musical", movie7.get().getId());

        db.createRating("Best musical", "10", "10", "David", movie7.get().getId(), "");
        db.createRating("Feel good movies", "10", "10", "David", movie7.get().getId(), "");

        db.createMovie("Bring It On", "Peyton Reed", "2000", "1h. 38min.", "Jessica Bendinger",
                "A chick who’s on a cheerleading team that steals routines from another way cooler school ends up being captain and changes her team’s ways. They end up losing to the way cooler team.");

        var movie8 = db.getMovieWithTitle("Bring It On");
        db.createTag("Comedy", movie8.get().getId());
        db.createTag("Romance", movie8.get().getId());
        db.createTag("Sport", movie8.get().getId());

        db.createRating("Best Cheerleading movie", "6", "6", "David", movie8.get().getId(), "");
        db.createRating("Cutest Outfits", "3", "3", "David", movie8.get().getId(), "");

        db.createMovie("Enchanted", "Kevin Lima", "2007", "1h. 47min.", "Bill Kelly",
                "An animated fairy-tale chick ends up falling into a well and ends up getting with a man who’s engaged. She was also engaged and having her wedding day before she fell into the well. They end up having an emotional affair and get together at the end of the movie. It’s so cute and their partners get together and live way better lives than them.");

        var movie9 = db.getMovieWithTitle("Enchanted");
        db.createTag("Animation", movie9.get().getId());
        db.createTag("Adventure", movie9.get().getId());
        db.createTag("Comedy", movie9.get().getId());

        db.createRating("Best Movie of ALL TIME", "10", "10", "David", movie9.get().getId(), "");
        db.createRating("Best side character of all time", "3", "3", "David", movie9.get().getId(), "");
        db.createRating("Best storyline of all time", "10", "10", "David", movie9.get().getId(), "");
        db.createRating("Most satisfying ending of all time", "4", "4", "David", movie9.get().getId(), "");

        db.createMovie("The Road to El Dorado", "Bibo Bergeron, Don Paul, Jeffrey Katzenberg", "2000", "1h. 29min.", "Ted Elliot, Terry Rossio, Karey Kirkpatrick",
                "Two brother-like friends end up stranded on an island and deceive a bunch of natives into thinking they’re gods. They end up saving the island and leaving it though.");

        var movie10 = db.getMovieWithTitle("The Road to El Dorado");
        db.createTag("Animation", movie10.get().getId());
        db.createTag("Adventure", movie10.get().getId());
        db.createTag("Comedy", movie10.get().getId());

        db.createRating("BEST movie of all time", "10", "10", "David", movie10.get().getId(), "");
        db.createRating("Best bros United", "8", "8", "David", movie10.get().getId(), "");
        db.createRating("Summer-time movies", "9", "9", "David", movie10.get().getId(), "");
        db.createRating("Hottest animated character", "10", "10", "David", movie10.get().getId(), "");

        db.createMovie("Barbie", "Greta Gerwig", "2023", "1h. 45min.", "Greta Gerwig, Noah Baumbach",
                "Barbie becomes imperfect and wants to fix herself so she goes to the real world and then wants to stay imperfect. Ken is also a menace.");

        var movie11 = db.getMovieWithTitle("Barbie");
        db.createTag("Adventure", movie11.get().getId());
        db.createTag("Comedy", movie11.get().getId());
        db.createTag("Fantasy", movie11.get().getId());

        db.createRating("Camp movies", "10", "10", "David", movie11.get().getId(), "");
        db.createRating("Existential Crises", "5", "5", "David", movie11.get().getId(), "");
        db.createRating("2023 Movies", "8", "8", "David", movie11.get().getId(), "");

        db.createMovie("Barbie as The Princess and the Pauper", "William Lau", "2004", "1h. 25min.", "Cliff Ruby, Elana Lesser, Mark Twain",
                "An adaption of the prince and the pauper but with Barbie.");

        var movie12 = db.getMovieWithTitle("Barbie as The Princess and the Pauper");
        db.createTag("Animation", movie12.get().getId());
        db.createTag("Comedy", movie12.get().getId());
        db.createTag("Family", movie12.get().getId());

        db.createRating("Best Barbie movie of all time", "10", "10", "David", movie12.get().getId(), "");
        db.createRating("Best animated movie of all time", "5", "5", "David", movie12.get().getId(), "");
        db.createRating("Cutest Couples in Movies", "2", "2", "David", movie12.get().getId(), "");

        db.createMovie("Cinderella", "Robert Iscove", "1997", "1h. 28min.", "Oscar Hammerstein 2, Robert L. Freedman, Charles Perrault",
                "A chick who can’t throw hands ends up meeting a prince and they live happily ever after.");

        var movie13 = db.getMovieWithTitle("Cinderella");
        db.createTag("Family", movie13.get().getId());
        db.createTag("Fantasy", movie13.get().getId());
        db.createTag("Musical", movie13.get().getId());

        db.createRating("Live-Action Princess Movie", "4", "4", "David", movie13.get().getId(), "");
        db.createRating("Confusing movies", "10", "10", "David", movie13.get().getId(), "");

        db.createMovie("The Lion King", "Roger Allers, Rob Minkoff", "1994", "1h. 28min.", "Irene Mecchi, Jonathan Roberts, Linda Woolverton",
                "Young lion cub murders his father cause he can’t follow instructions. He then fights his uncle to take his father’s place as leader of the lion pack.");

        var movie14 = db.getMovieWithTitle("The Lion King");
        db.createTag("Animation", movie14.get().getId());
        db.createTag("Adventure", movie14.get().getId());
        db.createTag("Drama", movie14.get().getId());

        db.createRating("Best animated movie of all time", "7", "7", "David", movie14.get().getId(), "");

        db.createMovie("The Princess and the Frog", "Ron Clements, John Musker", "2009", "1h. 37min.", "Ron Clements, John Musker, Greg Erb",
                "A girl who has life figured out gets bothered by a frog and her life becomes awful. But they fall in love and live happily ever after.");

        var movie15 = db.getMovieWithTitle("The Princess and the Frog");
        db.createTag("Animation", movie15.get().getId());
        db.createTag("Adventure", movie15.get().getId());
        db.createTag("Comedy", movie15.get().getId());

        db.createRating("Animated Disney Movie Goodness", "2", "2", "David", movie15.get().getId(), "");
        db.createRating("Princess PrincessNess", "8", "8", "David", movie15.get().getId(), "");
        db.createRating("Pretty Clothes", "6", "6", "David", movie15.get().getId(), "");

        db.createMovie("Tangled", "Nathan Greno, Byron Howard", "2010", "1h. 40min.", "Dan Fogelman, Jacob Grimm, Wilhelm Grimm",
                "A girl leaves the tower that her mom doesn’t want her to and falls in love. She was also imprisoned in said tower for years. And stolen from her parents.");

        var movie16 = db.getMovieWithTitle("Tangled");
        db.createTag("Animation", movie16.get().getId());
        db.createTag("Adventure", movie16.get().getId());
        db.createTag("Comedy", movie16.get().getId());

        db.createRating("Disney Princess Movie", "10", "10", "David", movie16.get().getId(), "");
        db.createRating("Quirky Disney Character rating", "7", "7", "David", movie16.get().getId(), "");
        db.createRating("Best animated movie", "8", "8", "David", movie16.get().getId(),"");

        db.createMovie("Mulan", "Tony Bancroft, Barry Cook", "1998", "1h. 27 min.", "Robert D. San Souci, Rita Hsiao, Chris Sanders",
                "A girl goes to war in place of her father and saves China. She also has a pet dragon and takes home a hot general.");

        var movie17 = db.getMovieWithTitle("Mulan");
        db.createTag("Animation", movie17.get().getId());
        db.createTag("Adventure", movie17.get().getId());
        db.createTag("Comedy", movie17.get().getId());

        db.createRating("Disney Princess Movie", "10", "10", "David", movie17.get().getId(), "");
        db.createRating("Princess PrincessNess", "8", "8", "David", movie17.get().getId(), "");
        db.createRating("Best animated movie of all time", "10", "10", "David", movie17.get().getId(), "");
        db.createRating("Best Movies of all time", "10", "10", "David", movie17.get().getId(), "");

        db.createMovie("Shang-Chi and the Legend of the 10 Rings", "Destin Daniel Cretton", "2021", "2h. 12 min.", "Dave Callaham, Destin Daniel Cretton, Andrew Lanham",
                "A boy becomes a superhero because he has to fight his father or something.");

        var movie18 = db.getMovieWithTitle("Shang-Chi and the Legend of the 10 Rings");
        db.createTag("Action", movie18.get().getId());
        db.createTag("Adventure", movie18.get().getId());
        db.createTag("Comedy", movie18.get().getId());

        db.createRating("Superhero origins", "4", "4", "David", movie18.get().getId(), "");
        db.createRating("Cutest male lead", "9", "9", "David", movie18.get().getId(), "");
        db.createRating("Best weapons", "7", "7", "David", movie18.get().getId(), "");

        db.createMovie("Everything Everywhere All at Once", "Daniel Kwan, Daniel Scheinert", "2022", "2h. 19min.", "Daniel Kwan, Daniel Scheinert",
                "A middle-aged Chinese immigrant goes on an adventure exploring other universes.");

        var movie19 = db.getMovieWithTitle("Everything Everywhere All at Once");
        db.createTag("Action", movie19.get().getId());
        db.createTag("Adventure", movie19.get().getId());
        db.createTag("Comedy", movie19.get().getId());

        db.createRating("Costume rating", "7", "7", "David", movie19.get().getId(), "");
        db.createRating("Weirdest people", "4", "4", "David", movie19.get().getId(), "");
        db.createRating("Coolest plot", "3", "3", "David", movie19.get().getId(), "");

        db.createMovie("Spider-Man: Across the Spider-Verse", "Joaquim Dos Santos, Kemp Powers, Justin K. Thompson", "2023", "2h. 20min.", "Phil Lord, Christopher Miller, Dave Callaham",
                "Young Spider Boy has another spider-verse adventure.");

        var movie20 = db.getMovieWithTitle("Spider-Man: Across the Spider-Verse");
        db.createTag("Animation", movie20.get().getId());
        db.createTag("Action", movie20.get().getId());
        db.createTag("Adventure", movie20.get().getId());

        db.createRating("Best animated movie", "10", "10", "David", movie20.get().getId(), "");
        db.createRating("Movie of the year", "9", "9", "David", movie20.get().getId(), "");
        db.createRating("Character drawing rating", "3", "3", "David", movie20.get().getId(), "");
        db.createRating("Animation awesomeness", "4", "4", "David", movie20.get().getId(), "");

        //DataBase population for actors:

        db.createActor("Julie Andrews", "10/01/1935", "Mary Poppins, The Sound of Music, The Americanization of Emily, Thoroughly Modern Millie, Hawaii, Victor/Victoria, The Princess Diaries, The Princess Diaries 2: Royal Engagement, The Pink Panther, Despicable Me");

        db.createActor("Christopher Plummer", "12/13/1929", "The Sound of Music, Beginners, The Insider, Knives Out, The Girl with the Dragon Tattoo, The Last Station, Up, A Beautiful Mind, The Man Who Knew Infinity, 12 Monkeys");

        db.createActor("Eleanor Parker", "06/26/1922", "The Sound of Music, Detective Story, Caged, Interrupted Melody, Of Human Bondage, The Man with the Golden Arm, Scaramouche, The Naked Jungle, An American in Paris, A Hole in the Head");

        db.createActor("Zac Efron", "10/18/1987", "High School Musical, High School Musical 2, High School Musical 3: Senior Year, The Greatest Showman, Neighbors, 17 Again, Hairspray, The Lucky One, Baywatch, Extremely Wicked, Shockingly Evil and Vile");

        db.createActor("Matthew Perry", "08/19/1922", "Friends, The Whole Nine Yards, 17 Again, The Odd Couple, Studio 60 on the Sunset Strip, The Good Fight, The Ron Clark Story, The Whole Ten Yards, Fools Rush In, The End of Longing");

        db.createActor("Leslie Mann", "03/26/1972", "Knocked Up, This Is 40, The 40-Year-Old Virgin, Blockers, Funny People, The Cable Guy, George of the Jungle, Big Daddy, 17 Again, The Croods: A New Age");

        db.createActor("Jay Baruchel", "04/09/1982", "How to Train Your Dragon, This Is the End, How to Train Your Dragon 2, Knocked Up, She's Out of My League, Tropic Thunder, The Sorcerer's Apprentice, Million Dollar Baby, The Art of the Steal, How to Train Your Dragon: The Hidden World");

        db.createActor("Gerard Butler", "11/13/1969", "300, Law Abiding Citizen, Olympus Has Fallen, P.S. I Love You, The Phantom of the Opera, How to Train Your Dragon, 300: Rise of an Empire, The Ugly Truth, Gods of Egypt, Machine Gun Preacher");

        db.createActor("Christopher Mintz-Plasse", "06/20/1989", "Superbad, Role Models, Kick-Ass, Kick-Ass 2, How to Train Your Dragon, This Is the End, Neighbors, Fright Night, The To Do List, ParaNorman");

        db.createActor("Brad Garrett", "04/14/1960", "Everybody Loves Raymond, Ratatouille, Finding Nemo, Tangled, Night at the Museum, Teen Titans Go! To the Movies, Hercules, Music and Lyrics, The Pacifier, Garfield: The Movie");
        db.createActor("Lou Romano", "04/15/1971", "Ratatouille, The Incredibles, Cars, Inside Out, Up, The Incredibles 2, Bolt, Ratatouille: Food Fight, Ratatouille: Rat's House, Ratatouille: The Video Game");

        db.createActor("Paton Oswalt", "01/27/1969", "Ratatouille, The King of Queens, Young Adult, Veep, United States of Tara, Reno 911!, A.P. Bio, Magnolia, Two and a Half Men, Big Fan");

        db.createActor("Michael J. Fox", "06/09/1961", "Back to the Future, Family Ties, Back to the Future Part II, Back to the Future Part III, Spin City, Teen Wolf, Doc Hollywood, The Frighteners, Stuart Little, The Good Wife");

        db.createActor("Jim Varney", "06/15/1949", "Ernest Saves Christmas, Ernest Goes to Camp, Ernest Scared Stupid, Ernest Rides Again, Ernest Goes to Jail, Ernest Goes to School, Ernest Goes to Africa, Ernest in the Army, Ernest Goes to Splash Mountain, Ernest's Greatest Hits 1");

        db.createActor("Corey Burton", "08/03/1955", "Voice of Captain Hook, Voice of Ludwig Von Drake, Star Wars: The Clone Wars, The Transformers: The Movie, Gargoyles, Batman: The Animated Series, Transformers: Dark of the Moon, The Rescuers Down Under, Atlantis: The Lost Empire");

        db.createActor("Brandy Norwood", "02/11/1979", "Cinderella, Moesha, I Still Know What You Did Last Summer, Set It Off, Double Platinum, The Game, Chicago, Thea, Temptation: Confessions of a Marriage Counselor, Osmosis Jones");

        db.createActor("Bernadette Peters", "02/28/1948", "Into the Woods, Sunday in the Park with George, Annie, The Jerk, Pennies from Heaven, The Princess and the Cobbler, Pinkalicious & Peterrific, Cinderella, Gypsy, The Odyssey");

        db.createActor("Veanne Cox", "01/19/1963", "You've Got Mail, Company, Two Weeks Notice, Erin Brockovich, Marci X, The Family Stone, Desperate Housewives, Nurse Jackie, The Prince of Egypt, Alice");

        db.createActor("Simu Liu", "04/19/1989", "Shang-Chi and the Legend of the Ten Rings, Kim's Convenience, Barbie, Awkward, Blood and Water, Orphan Black, Reno 911!, A.P. Bio, Magnolia, Big Fan");

        db.createActor("Awkwafina", "06/02/1988", "Crazy Rich Asians, The Farewell, Ocean's 8, Raya and the Last Dragon, Jumanji: The Next Level, The Dark Crystal: Age of Resistance, Nora from Queens, Paradise Hills, Storks, Bad Rap");
    }
}
