import UIKit
import app

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, TalkListView, UISearchBarDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
    
    var presenter: TalkListPresenter!
    
    var talks: [Talk] = [] {
        didSet {
            self.tableView.reloadData()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        presenter = TalkListPresenter(localView: self)
        presenter.onCreate()
        presenter.loadTalks()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func showTalks(talks: [Talk]) {
        self.talks = talks
    }
    
    func showLoading(loading: Bool) {
        self.displayLoading(b: loading)
    }
    
    func displayError(e: KotlinException) {
        e.printStackTrace()
    }

    
    func displayLoading(b: Bool) {
        if b {
            activityIndicator.startAnimating()
        } else {
            activityIndicator.stopAnimating()
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.talks.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "TalkCell") as! TalkTableViewCell
        let talk = self.talks[indexPath.row]
        cell.hoursLabel.text = talk.timeSlotMultiline()
        cell.titleLabel.text = talk.title
        cell.subtitleLabel.text = talk.details()
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.tableView.deselectRow(at: indexPath, animated: true)
        performSegue(withIdentifier: "showDetail", sender: "talkId")
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let talk: Talk = self.talks[self.tableView.indexPathForSelectedRow?.row ?? 0]
        let talkId = talk.id
        let detailController = segue.destination as! DetailViewController
        detailController.talkId = talkId
    }

    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        presenter.filter(text: searchText)
    }
}

class TalkTableViewCell: UITableViewCell {
    
    @IBOutlet weak var hoursLabel: UILabel!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var subtitleLabel: UILabel!
}
